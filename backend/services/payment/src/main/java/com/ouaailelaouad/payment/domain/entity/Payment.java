package com.ouaailelaouad.payment.domain.entity;

import com.ouaailelaouad.payment.domain.valueobject.Money;
import com.ouaailelaouad.payment.domain.valueobject.PaymentMethod;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String customerId;

    @Embedded
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private String transactionId; // From payment gateway (Stripe, PayPal)

    private String failureReason;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Domain logic
    public void markAsPaid(String transactionId) {
        this.status = PaymentStatus.PAID;
        this.transactionId = transactionId;
        this.failureReason = null;
    }

    public void markAsFailed(String reason) {
        this.status = PaymentStatus.FAILED;
        this.failureReason = reason;
        this.transactionId = null;
    }

    public void markAsPending() {
        this.status = PaymentStatus.PENDING;
    }

    public boolean isPaid() {
        return this.status == PaymentStatus.PAID;
    }

    public boolean isFailed() {
        return this.status == PaymentStatus.FAILED;
    }

    @PrePersist
    public void prePersist() {
        if (this.reference == null) {
            this.reference = generateReference();
        }
        if (this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }

    private String generateReference() {
        return "PAY-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }
}