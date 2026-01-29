package com.example.demo.dto;

import com.example.demo.entity.ParkingSpot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotDTO {
    private Long id;
    
    @NotBlank(message = "Spot number is required")
    private String spotNumber;
    
    private ParkingSpot.SpotStatus status;
    
    @NotNull(message = "Parking lot ID is required")
    private Long parkingLotId;
    
    private String parkingLotName; // For response only
}
