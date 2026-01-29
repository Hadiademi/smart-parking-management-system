package com.example.demo.repository;

import com.example.demo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findByUserId(Long userId);
    
    List<Subscription> findByStatus(Subscription.SubscriptionStatus status);
    
    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.status = 'ACTIVE' AND s.endDate > :currentDate")
    Optional<Subscription> findActiveSubscriptionByUserId(Long userId, LocalDate currentDate);
    
    @Query("SELECT s FROM Subscription s WHERE s.endDate < :currentDate AND s.status = 'ACTIVE'")
    List<Subscription> findExpiredSubscriptions(LocalDate currentDate);
}
