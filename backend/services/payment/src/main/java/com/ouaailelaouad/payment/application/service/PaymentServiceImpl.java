package com.ouaailelaouad.payment.application.service;

import com.ouaailelaouad.payment.application.client.CustomerClient;
import com.ouaailelaouad.payment.application.dto.PaymentRequest;
import com.ouaailelaouad.payment.application.dto.PaymentResponse;
import com.ouaailelaouad.payment.domain.entity.Payment;
import com.ouaailelaouad.payment.domain.event.PaymentConfirmationEvent;
import com.ouaailelaouad.payment.domain.repository.PaymentRepository;
import com.ouaailelaouad.payment.domain.valueobject.Money;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import com.ouaailelaouad.payment.infrastructure.messaging.PaymentConfirmationProducer;
import com.ouaailelaouad.payment.presentation.exception.CustomerNotFoundException;
import com.ouaailelaouad.payment.presentation.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerClient customerClient;
    private final PaymentConfirmationProducer confirmationProducer;

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        log.info("Creating payment for order: {}", request.getOrderId());

        // 1. Validate customer exists
        validateCustomerExists(request.getCustomerId());

        // 2. Create payment entity
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .amount(Money.of(request.getAmount(), request.getCurrency()))
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .build();

        // 3. Process payment (mock payment gateway)
        boolean paymentSuccess = processPaymentGateway(request);

        if (paymentSuccess) {
            payment.markAsPaid("TXN-" + System.currentTimeMillis());
            log.info("Payment successful for order: {}", request.getOrderId());
        } else {
            payment.markAsFailed("Payment gateway declined");
            log.error("Payment failed for order: {}", request.getOrderId());
        }

        // 4. Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // 5. Send confirmation event to Kafka
        sendPaymentConfirmation(savedPayment);

        return mapToResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByReference(String reference) {
        Payment payment = paymentRepository.findByReference(reference)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with reference: " + reference));
        return mapToResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for order: " + orderId));
        return mapToResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByCustomerId(String customerId, Pageable pageable) {
        return paymentRepository.findByCustomerId(customerId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByStatus(PaymentStatus status, Pageable pageable) {
        return paymentRepository.findByStatus(status, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public PaymentResponse refundPayment(String reference) {
        Payment payment = paymentRepository.findByReference(reference)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with reference: " + reference));

        if (!payment.isPaid()) {
            throw new IllegalStateException("Only paid payments can be refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        Payment updatedPayment = paymentRepository.save(payment);

        // Send refund event
        sendPaymentConfirmation(updatedPayment);

        return mapToResponse(updatedPayment);
    }

    @Override
    @Transactional
    public PaymentResponse cancelPayment(String reference) {
        Payment payment = paymentRepository.findByReference(reference)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with reference: " + reference));

        if (payment.isPaid()) {
            throw new IllegalStateException("Cannot cancel a paid payment. Use refund instead");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        Payment updatedPayment = paymentRepository.save(payment);

        return mapToResponse(updatedPayment);
    }

    // Private helper methods

    private void validateCustomerExists(String customerId) {
        Boolean exists = customerClient.existsById(customerId);
        if (exists == null || !exists) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
    }

    private boolean processPaymentGateway(PaymentRequest request) {
        // TODO: Integrate with real payment gateway (Stripe, PayPal)
        // For now, mock success (90% success rate)
        return Math.random() > 0.1;
    }

    private void sendPaymentConfirmation(Payment payment) {
        PaymentConfirmationEvent event = PaymentConfirmationEvent.builder()
                .paymentReference(payment.getReference())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount().getAmount())
                .currency(payment.getAmount().getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .timestamp(LocalDateTime.now())
                .build();

        confirmationProducer.sendConfirmation(event);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .reference(payment.getReference())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount().getAmount())
                .currency(payment.getAmount().getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}