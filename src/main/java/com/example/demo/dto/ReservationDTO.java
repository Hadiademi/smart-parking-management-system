package com.example.demo.dto;

import com.example.demo.entity.Reservation;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
    
    private Double price; // Calculated automatically
    
    private Reservation.ReservationStatus status;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Parking spot ID is required")
    private Long parkingSpotId;
    
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
    
    // For response only
    private String userName;
    private String spotNumber;
    private String vehicleLicensePlate;
}
