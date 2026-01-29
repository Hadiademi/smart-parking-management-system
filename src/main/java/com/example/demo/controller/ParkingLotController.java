package com.example.demo.controller;

import com.example.demo.dto.ParkingLotDTO;
import com.example.demo.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    public ResponseEntity<ParkingLotDTO> createParkingLot(@Valid @RequestBody ParkingLotDTO parkingLotDTO) {
        ParkingLotDTO createdParkingLot = parkingLotService.createParkingLot(parkingLotDTO);
        return new ResponseEntity<>(createdParkingLot, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDTO> getParkingLotById(@PathVariable Long id) {
        ParkingLotDTO parkingLot = parkingLotService.getParkingLotById(id);
        return ResponseEntity.ok(parkingLot);
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotDTO>> getAllParkingLots() {
        List<ParkingLotDTO> parkingLots = parkingLotService.getAllParkingLots();
        return ResponseEntity.ok(parkingLots);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ParkingLotDTO>> searchByName(@RequestParam String name) {
        List<ParkingLotDTO> parkingLots = parkingLotService.searchParkingLotsByName(name);
        return ResponseEntity.ok(parkingLots);
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<ParkingLotDTO>> searchByAddress(@RequestParam String address) {
        List<ParkingLotDTO> parkingLots = parkingLotService.searchParkingLotsByAddress(address);
        return ResponseEntity.ok(parkingLots);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotDTO> updateParkingLot(
            @PathVariable Long id,
            @Valid @RequestBody ParkingLotDTO parkingLotDTO) {
        ParkingLotDTO updatedParkingLot = parkingLotService.updateParkingLot(id, parkingLotDTO);
        return ResponseEntity.ok(updatedParkingLot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }
}
