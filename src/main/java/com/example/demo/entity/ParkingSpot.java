package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_spots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Spot number is required")
    @Column(nullable = false)
    private String spotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotStatus status = SpotStatus.FREE;

    // Relationship with ParkingLot (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    // Relationship with Reservation (One-to-Many)
    @OneToMany(mappedBy = "parkingSpot", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public enum SpotStatus {
        FREE, OCCUPIED, RESERVED, MAINTENANCE
    }
}
