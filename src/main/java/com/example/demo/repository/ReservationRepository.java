package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByUserId(Long userId);
    
    List<Reservation> findByParkingSpotId(Long spotId);
    
    List<Reservation> findByVehicleId(Long vehicleId);
    
    List<Reservation> findByStatus(Reservation.ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.status = :status")
    List<Reservation> findByUserIdAndStatus(Long userId, Reservation.ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.parkingSpot.id = :spotId AND r.status = 'ACTIVE' " +
           "AND ((r.startTime BETWEEN :startTime AND :endTime) OR " +
           "(r.endTime BETWEEN :startTime AND :endTime) OR " +
           "(:startTime BETWEEN r.startTime AND r.endTime))")
    List<Reservation> findConflictingReservations(Long spotId, LocalDateTime startTime, LocalDateTime endTime);
    
    @Query("SELECT r FROM Reservation r WHERE r.endTime < :now AND r.status = 'ACTIVE'")
    List<Reservation> findExpiredReservations(LocalDateTime now);
}
