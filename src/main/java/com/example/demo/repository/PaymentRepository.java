package com.example.demo.repository;

import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByReservationId(Long reservationId);
    
    List<Payment> findByStatus(Payment.PaymentStatus status);
    
    List<Payment> findByMethod(Payment.PaymentMethod method);
    
    @Query("SELECT p FROM Payment p WHERE p.timestamp BETWEEN :startDate AND :endDate")
    List<Payment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
    Double getTotalRevenue();
    
    @Query("SELECT p FROM Payment p JOIN p.reservation r WHERE r.user.id = :userId")
    List<Payment> findByUserId(Long userId);
}
