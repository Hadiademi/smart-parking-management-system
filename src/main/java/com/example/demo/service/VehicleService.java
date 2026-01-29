package com.example.demo.service;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Vehicle;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        // Check if license plate already exists
        if (vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())) {
            throw new DuplicateResourceException("License plate already exists: " + vehicleDTO.getLicensePlate());
        }
        
        User user = userRepository.findById(vehicleDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", vehicleDTO.getUserId()));
        
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setUser(user);
        
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToDTO(savedVehicle);
    }

    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", id));
        return mapToDTO(vehicle);
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getVehiclesByUserId(Long userId) {
        return vehicleRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", id));
        
        // Check if new license plate is already taken by another vehicle
        if (!vehicle.getLicensePlate().equals(vehicleDTO.getLicensePlate()) &&
            vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())) {
            throw new DuplicateResourceException("License plate already exists: " + vehicleDTO.getLicensePlate());
        }
        
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setType(vehicleDTO.getType());
        
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToDTO(updatedVehicle);
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", id);
        }
        vehicleRepository.deleteById(id);
    }

    private VehicleDTO mapToDTO(Vehicle vehicle) {
        VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
        dto.setUserId(vehicle.getUser().getId());
        dto.setUserName(vehicle.getUser().getName());
        return dto;
    }
}
