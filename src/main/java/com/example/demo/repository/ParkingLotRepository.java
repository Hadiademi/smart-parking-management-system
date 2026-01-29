package com.example.demo.repository;

import com.example.demo.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    
    List<ParkingLot> findByNameContainingIgnoreCase(String name);
    
    List<ParkingLot> findByAddressContainingIgnoreCase(String address);
    
    @Query("SELECT p FROM ParkingLot p WHERE p.totalSpots >= :minSpots")
    List<ParkingLot> findByMinimumCapacity(Integer minSpots);
}
