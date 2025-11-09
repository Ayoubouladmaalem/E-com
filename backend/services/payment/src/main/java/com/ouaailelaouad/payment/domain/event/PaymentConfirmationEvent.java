package com.ouaailelaouad.payment.domain.event;

import com.ouaailelaouad.payment.domain.valueobject.PaymentMethod;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentConfirmationEvent {

    private String paymentReference;
    private Long orderId;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime timestamp;
}