package com.example.demo.repository;

import com.example.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    
    boolean existsByLicensePlate(String licensePlate);
    
    List<Vehicle> findByUserId(Long userId);
    
    List<Vehicle> findByType(Vehicle.VehicleType type);
}
