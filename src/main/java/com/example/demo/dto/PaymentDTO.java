package com.example.demo.dto;

import com.example.demo.entity.Payment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be greater than 0")
    private Double amount;
    
    private LocalDateTime timestamp;
    
    @NotNull(message = "Payment method is required")
    private Payment.PaymentMethod method;
    
    private Payment.PaymentStatus status;
    
    @NotNull(message = "Reservation ID is required")
    private Long reservationId;
}
