package com.example.demo.dto;

import com.example.demo.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    
    @NotBlank(message = "License plate is required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "License plate must contain only uppercase letters, numbers, and hyphens")
    private String licensePlate;
    
    @NotNull(message = "Vehicle type is required")
    private Vehicle.VehicleType type;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String userName; // For response only
}
