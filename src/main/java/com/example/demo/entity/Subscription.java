package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Monthly fee is required")
    @Min(value = 0, message = "Monthly fee must be greater than 0")
    @Column(nullable = false)
    private Double monthlyFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    // Relationship with User (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum SubscriptionStatus {
        ACTIVE, EXPIRED, CANCELLED, SUSPENDED
    }

    // Helper method to check if subscription is active
    @Transient
    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE && 
               LocalDate.now().isBefore(endDate);
    }
}
