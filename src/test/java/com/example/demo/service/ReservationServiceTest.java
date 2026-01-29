package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.*;
import com.example.demo.exception.InvalidReservationTimeException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SpotNotAvailableException;
import com.example.demo.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReservationService reservationService;

    private User testUser;
    private ParkingSpot testSpot;
    private Vehicle testVehicle;
    private ParkingLot testParkingLot;
    private ReservationDTO testReservationDTO;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole(User.UserRole.CLIENT);

        // Setup test parking lot
        testParkingLot = new ParkingLot();
        testParkingLot.setId(1L);
        testParkingLot.setName("Test Parking Lot");
        testParkingLot.setAddress("Test Address");
        testParkingLot.setTotalSpots(100);

        // Setup test parking spot
        testSpot = new ParkingSpot();
        testSpot.setId(1L);
        testSpot.setSpotNumber("A-101");
        testSpot.setStatus(ParkingSpot.SpotStatus.FREE);
        testSpot.setParkingLot(testParkingLot);

        // Setup test vehicle
        testVehicle = new Vehicle();
        testVehicle.setId(1L);
        testVehicle.setLicensePlate("ABC-123");
        testVehicle.setType(Vehicle.VehicleType.CAR);
        testVehicle.setUser(testUser);

        // Setup test reservation DTO
        testReservationDTO = new ReservationDTO();
        testReservationDTO.setUserId(1L);
        testReservationDTO.setParkingSpotId(1L);
        testReservationDTO.setVehicleId(1L);
        testReservationDTO.setStartTime(LocalDateTime.now().plusHours(1));
        testReservationDTO.setEndTime(LocalDateTime.now().plusHours(3));
    }

    @Test
    void createReservation_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingSpotRepository.findById(1L)).thenReturn(Optional.of(testSpot));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(testVehicle));
        when(reservationRepository.findConflictingReservations(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(subscriptionRepository.findActiveSubscriptionByUserId(any(), any()))
                .thenReturn(Optional.empty());
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));
        when(parkingSpotRepository.save(any(ParkingSpot.class))).thenReturn(testSpot);

        // Act
        ReservationDTO result = reservationService.createReservation(testReservationDTO);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(parkingSpotRepository, times(1)).save(any(ParkingSpot.class));
    }

    @Test
    void createReservation_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.createReservation(testReservationDTO);
        });
    }

    @Test
    void createReservation_SpotNotAvailable_ThrowsException() {
        // Arrange
        testSpot.setStatus(ParkingSpot.SpotStatus.OCCUPIED);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingSpotRepository.findById(1L)).thenReturn(Optional.of(testSpot));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(testVehicle));

        // Act & Assert
        assertThrows(SpotNotAvailableException.class, () -> {
            reservationService.createReservation(testReservationDTO);
        });
    }

    @Test
    void createReservation_InvalidTime_ThrowsException() {
        // Arrange
        testReservationDTO.setStartTime(LocalDateTime.now().minusHours(1)); // Past time

        // Act & Assert
        assertThrows(InvalidReservationTimeException.class, () -> {
            reservationService.createReservation(testReservationDTO);
        });
    }

    @Test
    void calculatePrice_BasicCalculation() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 12, 0);
        when(subscriptionRepository.findActiveSubscriptionByUserId(any(), any()))
                .thenReturn(Optional.empty());

        // Act
        double price = reservationService.calculatePrice(
                startTime, endTime, Vehicle.VehicleType.CAR, 1L);

        // Assert
        assertTrue(price > 0);
        assertEquals(4.0, price, 0.01); // 2 hours * 2.0 per hour
    }

    @Test
    void calculatePrice_WithSubscriptionDiscount() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 12, 0);
        
        Subscription subscription = new Subscription();
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        subscription.setEndDate(LocalDate.now().plusDays(30));
        
        when(subscriptionRepository.findActiveSubscriptionByUserId(any(), any()))
                .thenReturn(Optional.of(subscription));

        // Act
        double price = reservationService.calculatePrice(
                startTime, endTime, Vehicle.VehicleType.CAR, 1L);

        // Assert
        assertTrue(price > 0);
        assertEquals(2.8, price, 0.01); // 2 hours * 2.0 * 0.7 (subscription discount)
    }

    @Test
    void calculatePrice_ElectricVehicleDiscount() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 12, 0);
        when(subscriptionRepository.findActiveSubscriptionByUserId(any(), any()))
                .thenReturn(Optional.empty());

        // Act
        double price = reservationService.calculatePrice(
                startTime, endTime, Vehicle.VehicleType.ELECTRIC, 1L);

        // Assert
        assertTrue(price > 0);
        assertEquals(3.6, price, 0.01); // 2 hours * 2.0 * 0.9 (electric discount)
    }

    @Test
    void cancelReservation_Success() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);
        reservation.setParkingSpot(testSpot);
        reservation.setUser(testUser);
        reservation.setVehicle(testVehicle);
        reservation.setStartTime(LocalDateTime.now().plusHours(1));
        reservation.setEndTime(LocalDateTime.now().plusHours(3));
        reservation.setPrice(10.0);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(parkingSpotRepository.save(any(ParkingSpot.class))).thenReturn(testSpot);

        // Act
        ReservationDTO result = reservationService.cancelReservation(1L);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(parkingSpotRepository, times(1)).save(any(ParkingSpot.class));
    }

    @Test
    void getReservationById_NotFound_ThrowsException() {
        // Arrange
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getReservationById(999L);
        });
    }
}
