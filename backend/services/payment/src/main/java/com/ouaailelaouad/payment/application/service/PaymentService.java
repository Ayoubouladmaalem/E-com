package com.ouaailelaouad.payment.application.service;

import com.ouaailelaouad.payment.application.dto.PaymentRequest;
import com.ouaailelaouad.payment.application.dto.PaymentResponse;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByReference(String reference);

    PaymentResponse getPaymentByOrderId(Long orderId);

    Page<PaymentResponse> getPaymentsByCustomerId(String customerId, Pageable pageable);

    Page<PaymentResponse> getPaymentsByStatus(PaymentStatus status, Pageable pageable);

    Page<PaymentResponse> getAllPayments(Pageable pageable);

    PaymentResponse refundPayment(String reference);

    PaymentResponse cancelPayment(String reference);
}