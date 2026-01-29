package com.example.demo.dto;

import com.example.demo.entity.Subscription;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long id;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "Monthly fee is required")
    @Min(value = 0, message = "Monthly fee must be greater than 0")
    private Double monthlyFee;
    
    private Subscription.SubscriptionStatus status;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String userName; // For response only
}
