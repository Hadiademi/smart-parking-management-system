package com.example.demo.controller;

import com.example.demo.dto.ParkingSpotDTO;
import com.example.demo.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<ParkingSpotDTO> createParkingSpot(@Valid @RequestBody ParkingSpotDTO spotDTO) {
        ParkingSpotDTO createdSpot = parkingSpotService.createParkingSpot(spotDTO);
        return new ResponseEntity<>(createdSpot, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotDTO> getParkingSpotById(@PathVariable Long id) {
        ParkingSpotDTO spot = parkingSpotService.getParkingSpotById(id);
        return ResponseEntity.ok(spot);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotDTO>> getAllParkingSpots() {
        List<ParkingSpotDTO> spots = parkingSpotService.getAllParkingSpots();
        return ResponseEntity.ok(spots);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableSpots() {
        List<ParkingSpotDTO> spots = parkingSpotService.getAvailableSpots();
        return ResponseEntity.ok(spots);
    }

    @GetMapping("/parking-lot/{parkingLotId}")
    public ResponseEntity<List<ParkingSpotDTO>> getSpotsByParkingLot(@PathVariable Long parkingLotId) {
        List<ParkingSpotDTO> spots = parkingSpotService.getSpotsByParkingLot(parkingLotId);
        return ResponseEntity.ok(spots);
    }

    @GetMapping("/parking-lot/{parkingLotId}/available")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableSpotsByParkingLot(@PathVariable Long parkingLotId) {
        List<ParkingSpotDTO> spots = parkingSpotService.getAvailableSpotsByParkingLot(parkingLotId);
        return ResponseEntity.ok(spots);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSpotDTO> updateSpotStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        ParkingSpotDTO updatedSpot = parkingSpotService.updateParkingSpotStatus(id, status);
        return ResponseEntity.ok(updatedSpot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable Long id) {
        parkingSpotService.deleteParkingSpot(id);
        return ResponseEntity.noContent().build();
    }
}
