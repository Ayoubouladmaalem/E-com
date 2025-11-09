package com.ouaailelaouad.payment.domain.repository;

import com.ouaailelaouad.payment.domain.entity.Payment;
import com.ouaailelaouad.payment.domain.valueobject.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReference(String reference);

    Optional<Payment> findByOrderId(Long orderId);

    Page<Payment> findByCustomerId(String customerId, Pageable pageable);

    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);

    boolean existsByReference(String reference);

    // Custom query examples (if needed in the future):
    // @Query("SELECT p FROM Payment p WHERE p.amount.amount > :minAmount AND p.status = :status")
    // List<Payment> findByAmountGreaterThanAndStatus(@Param("minAmount") BigDecimal minAmount, @Param("status") PaymentStatus status);
}