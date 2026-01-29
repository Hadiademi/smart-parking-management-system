package com.example.demo.repository;

import com.example.demo.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    
    List<ParkingSpot> findByParkingLotId(Long parkingLotId);
    
    List<ParkingSpot> findByStatus(ParkingSpot.SpotStatus status);
    
    List<ParkingSpot> findByParkingLotIdAndStatus(Long parkingLotId, ParkingSpot.SpotStatus status);
    
    @Query("SELECT COUNT(ps) FROM ParkingSpot ps WHERE ps.parkingLot.id = :parkingLotId AND ps.status = 'FREE'")
    Long countAvailableSpotsByParkingLotId(Long parkingLotId);
}
