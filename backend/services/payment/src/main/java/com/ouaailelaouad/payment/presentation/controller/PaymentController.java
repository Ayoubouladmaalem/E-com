package com.ouaailelaouad.payment.presentation.controller;

import com.ouaailelaouad.payment.application.dto.PaymentRequest;
import com.ouaailelaouad.payment.application.dto.PaymentResponse;
import com.ouaailelaouad.payment.application.service.PaymentService;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "API de gestion des paiements")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Créer un nouveau paiement")
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un paiement par ID")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reference/{reference}")
    @Operation(summary = "Récupérer un paiement par référence")
    public ResponseEntity<PaymentResponse> getPaymentByReference(@PathVariable String reference) {
        PaymentResponse response = paymentService.getPaymentByReference(reference);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Récupérer le paiement d'une commande")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentResponse response = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Récupérer les paiements d'un client")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsByCustomerId(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PaymentResponse> response = paymentService.getPaymentsByCustomerId(customerId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Récupérer les paiements par statut")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsByStatus(
            @PathVariable PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PaymentResponse> response = paymentService.getPaymentsByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les paiements avec pagination")
    public ResponseEntity<Page<PaymentResponse>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PaymentResponse> response = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/refund/{reference}")
    @Operation(summary = "Rembourser un paiement")
    public ResponseEntity<PaymentResponse> refundPayment(@PathVariable String reference) {
        PaymentResponse response = paymentService.refundPayment(reference);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/cancel/{reference}")
    @Operation(summary = "Annuler un paiement")
    public ResponseEntity<PaymentResponse> cancelPayment(@PathVariable String reference) {
        PaymentResponse response = paymentService.cancelPayment(reference);
        return ResponseEntity.ok(response);
    }
}