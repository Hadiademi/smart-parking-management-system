package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.*;
import com.example.demo.exception.InvalidReservationTimeException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SpotNotAvailableException;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final VehicleRepository vehicleRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper;

    // Pricing constants
    private static final double BASE_RATE_PER_HOUR = 2.0;
    private static final double NIGHT_RATE_MULTIPLIER = 0.8; // 20% discount at night
    private static final double WEEKEND_RATE_MULTIPLIER = 1.2; // 20% increase on weekends
    private static final double ELECTRIC_VEHICLE_DISCOUNT = 0.9; // 10% discount for electric vehicles
    private static final double SUBSCRIPTION_DISCOUNT = 0.7; // 30% discount for subscribers

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        // Validate times
        validateReservationTimes(reservationDTO.getStartTime(), reservationDTO.getEndTime());
        
        // Get entities
        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", reservationDTO.getUserId()));
        
        ParkingSpot spot = parkingSpotRepository.findById(reservationDTO.getParkingSpotId())
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSpot", reservationDTO.getParkingSpotId()));
        
        Vehicle vehicle = vehicleRepository.findById(reservationDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", reservationDTO.getVehicleId()));
        
        // Check if spot is available
        if (spot.getStatus() != ParkingSpot.SpotStatus.FREE) {
            throw new SpotNotAvailableException(spot.getId());
        }
        
        // Check for conflicting reservations
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                spot.getId(), 
                reservationDTO.getStartTime(), 
                reservationDTO.getEndTime()
        );
        
        if (!conflicts.isEmpty()) {
            throw new SpotNotAvailableException("Spot is already reserved for the selected time period");
        }
        
        // Calculate price
        double price = calculatePrice(
                reservationDTO.getStartTime(),
                reservationDTO.getEndTime(),
                vehicle.getType(),
                user.getId()
        );
        
        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setParkingSpot(spot);
        reservation.setVehicle(vehicle);
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setPrice(price);
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);
        
        // Update spot status
        spot.setStatus(ParkingSpot.SpotStatus.RESERVED);
        parkingSpotRepository.save(spot);
        
        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToDTO(savedReservation);
    }

    /**
     * Calculate parking price based on multiple factors:
     * - Duration
     * - Vehicle type
     * - Time of day (night discount)
     * - Day of week (weekend surcharge)
     * - Active subscription discount
     */
    public double calculatePrice(LocalDateTime startTime, LocalDateTime endTime, 
                                 Vehicle.VehicleType vehicleType, Long userId) {
        // Calculate duration in hours
        Duration duration = Duration.between(startTime, endTime);
        double hours = duration.toMinutes() / 60.0;
        
        // Base price
        double price = BASE_RATE_PER_HOUR * hours;
        
        // Apply time-based multipliers
        int startHour = startTime.getHour();
        if (startHour >= 22 || startHour < 6) {
            // Night discount (22:00 - 06:00)
            price *= NIGHT_RATE_MULTIPLIER;
        }
        
        // Weekend surcharge
        int dayOfWeek = startTime.getDayOfWeek().getValue();
        if (dayOfWeek == 6 || dayOfWeek == 7) { // Saturday or Sunday
            price *= WEEKEND_RATE_MULTIPLIER;
        }
        
        // Electric vehicle discount
        if (vehicleType == Vehicle.VehicleType.ELECTRIC) {
            price *= ELECTRIC_VEHICLE_DISCOUNT;
        }
        
        // Check for active subscription
        boolean hasActiveSubscription = subscriptionRepository
                .findActiveSubscriptionByUserId(userId, java.time.LocalDate.now())
                .isPresent();
        
        if (hasActiveSubscription) {
            price *= SUBSCRIPTION_DISCOUNT;
        }
        
        // Round to 2 decimal places
        return Math.round(price * 100.0) / 100.0;
    }

    private void validateReservationTimes(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException("Start time cannot be in the past");
        }
        
        if (endTime.isBefore(startTime)) {
            throw new InvalidReservationTimeException("End time must be after start time");
        }
        
        Duration duration = Duration.between(startTime, endTime);
        if (duration.toMinutes() < 15) {
            throw new InvalidReservationTimeException("Minimum reservation duration is 15 minutes");
        }
        
        if (duration.toHours() > 24) {
            throw new InvalidReservationTimeException("Maximum reservation duration is 24 hours");
        }
    }

    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return mapToDTO(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByStatus(String status) {
        Reservation.ReservationStatus reservationStatus = Reservation.ReservationStatus.valueOf(status.toUpperCase());
        return reservationRepository.findByStatus(reservationStatus).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        
        if (reservation.getStatus() != Reservation.ReservationStatus.ACTIVE) {
            throw new InvalidReservationTimeException("Only active reservations can be cancelled");
        }
        
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        
        // Free up the spot
        ParkingSpot spot = reservation.getParkingSpot();
        spot.setStatus(ParkingSpot.SpotStatus.FREE);
        parkingSpotRepository.save(spot);
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToDTO(updatedReservation);
    }

    public void completeExpiredReservations() {
        List<Reservation> expiredReservations = 
                reservationRepository.findExpiredReservations(LocalDateTime.now());
        
        for (Reservation reservation : expiredReservations) {
            reservation.setStatus(Reservation.ReservationStatus.EXPIRED);
            ParkingSpot spot = reservation.getParkingSpot();
            spot.setStatus(ParkingSpot.SpotStatus.FREE);
            parkingSpotRepository.save(spot);
        }
        
        reservationRepository.saveAll(expiredReservations);
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO dto = modelMapper.map(reservation, ReservationDTO.class);
        dto.setUserId(reservation.getUser().getId());
        dto.setParkingSpotId(reservation.getParkingSpot().getId());
        dto.setVehicleId(reservation.getVehicle().getId());
        dto.setUserName(reservation.getUser().getName());
        dto.setSpotNumber(reservation.getParkingSpot().getSpotNumber());
        dto.setVehicleLicensePlate(reservation.getVehicle().getLicensePlate());
        return dto;
    }
}
