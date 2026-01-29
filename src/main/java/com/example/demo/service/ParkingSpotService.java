package com.example.demo.service;

import com.example.demo.dto.ParkingSpotDTO;
import com.example.demo.entity.ParkingLot;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ModelMapper modelMapper;

    public ParkingSpotDTO createParkingSpot(ParkingSpotDTO spotDTO) {
        ParkingLot parkingLot = parkingLotRepository.findById(spotDTO.getParkingLotId())
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot", spotDTO.getParkingLotId()));
        
        ParkingSpot spot = new ParkingSpot();
        spot.setSpotNumber(spotDTO.getSpotNumber());
        spot.setStatus(ParkingSpot.SpotStatus.FREE);
        spot.setParkingLot(parkingLot);
        
        ParkingSpot savedSpot = parkingSpotRepository.save(spot);
        return mapToDTO(savedSpot);
    }

    public ParkingSpotDTO getParkingSpotById(Long id) {
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSpot", id));
        return mapToDTO(spot);
    }

    public List<ParkingSpotDTO> getAllParkingSpots() {
        return parkingSpotRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ParkingSpotDTO> getSpotsByParkingLot(Long parkingLotId) {
        return parkingSpotRepository.findByParkingLotId(parkingLotId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ParkingSpotDTO> getAvailableSpots() {
        return parkingSpotRepository.findByStatus(ParkingSpot.SpotStatus.FREE).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ParkingSpotDTO> getAvailableSpotsByParkingLot(Long parkingLotId) {
        return parkingSpotRepository.findByParkingLotIdAndStatus(
                parkingLotId, 
                ParkingSpot.SpotStatus.FREE
        ).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ParkingSpotDTO updateParkingSpotStatus(Long id, String status) {
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSpot", id));
        
        ParkingSpot.SpotStatus newStatus = ParkingSpot.SpotStatus.valueOf(status.toUpperCase());
        spot.setStatus(newStatus);
        
        ParkingSpot updatedSpot = parkingSpotRepository.save(spot);
        return mapToDTO(updatedSpot);
    }

    public void deleteParkingSpot(Long id) {
        if (!parkingSpotRepository.existsById(id)) {
            throw new ResourceNotFoundException("ParkingSpot", id);
        }
        parkingSpotRepository.deleteById(id);
    }

    private ParkingSpotDTO mapToDTO(ParkingSpot spot) {
        ParkingSpotDTO dto = modelMapper.map(spot, ParkingSpotDTO.class);
        dto.setParkingLotId(spot.getParkingLot().getId());
        dto.setParkingLotName(spot.getParkingLot().getName());
        return dto;
    }
}
