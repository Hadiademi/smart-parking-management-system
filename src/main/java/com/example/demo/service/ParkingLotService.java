package com.example.demo.service;

import com.example.demo.dto.ParkingLotDTO;
import com.example.demo.entity.ParkingLot;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ModelMapper modelMapper;

    public ParkingLotDTO createParkingLot(ParkingLotDTO parkingLotDTO) {
        ParkingLot parkingLot = modelMapper.map(parkingLotDTO, ParkingLot.class);
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        return mapToDTO(savedParkingLot);
    }

    public ParkingLotDTO getParkingLotById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot", id));
        return mapToDTO(parkingLot);
    }

    public List<ParkingLotDTO> getAllParkingLots() {
        return parkingLotRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ParkingLotDTO> searchParkingLotsByName(String name) {
        return parkingLotRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ParkingLotDTO> searchParkingLotsByAddress(String address) {
        return parkingLotRepository.findByAddressContainingIgnoreCase(address).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ParkingLotDTO updateParkingLot(Long id, ParkingLotDTO parkingLotDTO) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot", id));
        
        parkingLot.setName(parkingLotDTO.getName());
        parkingLot.setAddress(parkingLotDTO.getAddress());
        parkingLot.setTotalSpots(parkingLotDTO.getTotalSpots());
        
        ParkingLot updatedParkingLot = parkingLotRepository.save(parkingLot);
        return mapToDTO(updatedParkingLot);
    }

    public void deleteParkingLot(Long id) {
        if (!parkingLotRepository.existsById(id)) {
            throw new ResourceNotFoundException("ParkingLot", id);
        }
        parkingLotRepository.deleteById(id);
    }

    private ParkingLotDTO mapToDTO(ParkingLot parkingLot) {
        ParkingLotDTO dto = modelMapper.map(parkingLot, ParkingLotDTO.class);
        dto.setAvailableSpots(parkingLot.getAvailableSpots());
        return dto;
    }
}
