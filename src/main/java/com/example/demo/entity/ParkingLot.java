package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Parking lot name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @Min(value = 1, message = "Total spots must be at least 1")
    @Column(nullable = false)
    private Integer totalSpots;

    // Relationship with ParkingSpot (One-to-Many)
    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    // Calculated field
    @Transient
    public Integer getAvailableSpots() {
        return (int) parkingSpots.stream()
                .filter(spot -> spot.getStatus() == ParkingSpot.SpotStatus.FREE)
                .count();
    }
}
