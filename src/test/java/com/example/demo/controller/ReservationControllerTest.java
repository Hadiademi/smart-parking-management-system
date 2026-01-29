package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    private ReservationDTO testReservationDTO;

    @BeforeEach
    void setUp() {
        testReservationDTO = new ReservationDTO();
        testReservationDTO.setId(1L);
        testReservationDTO.setUserId(1L);
        testReservationDTO.setParkingSpotId(1L);
        testReservationDTO.setVehicleId(1L);
        testReservationDTO.setStartTime(LocalDateTime.now().plusHours(1));
        testReservationDTO.setEndTime(LocalDateTime.now().plusHours(3));
        testReservationDTO.setPrice(10.0);
        testReservationDTO.setStatus(Reservation.ReservationStatus.ACTIVE);
        testReservationDTO.setUserName("Test User");
        testReservationDTO.setSpotNumber("A-101");
        testReservationDTO.setVehicleLicensePlate("ABC-123");
    }

    @Test
    void createReservation_Success() throws Exception {
        // Arrange
        when(reservationService.createReservation(any(ReservationDTO.class)))
                .thenReturn(testReservationDTO);

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testReservationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(reservationService, times(1)).createReservation(any(ReservationDTO.class));
    }

    @Test
    void getReservationById_Success() throws Exception {
        // Arrange
        when(reservationService.getReservationById(1L)).thenReturn(testReservationDTO);

        // Act & Assert
        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.spotNumber").value("A-101"));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void getAllReservations_Success() throws Exception {
        // Arrange
        List<ReservationDTO> reservations = Arrays.asList(testReservationDTO);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        // Act & Assert
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].price").value(10.0));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void getReservationsByUserId_Success() throws Exception {
        // Arrange
        List<ReservationDTO> reservations = Arrays.asList(testReservationDTO);
        when(reservationService.getReservationsByUserId(1L)).thenReturn(reservations);

        // Act & Assert
        mockMvc.perform(get("/api/reservations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));

        verify(reservationService, times(1)).getReservationsByUserId(1L);
    }

    @Test
    void getReservationsByStatus_Success() throws Exception {
        // Arrange
        List<ReservationDTO> reservations = Arrays.asList(testReservationDTO);
        when(reservationService.getReservationsByStatus("ACTIVE")).thenReturn(reservations);

        // Act & Assert
        mockMvc.perform(get("/api/reservations/status/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        verify(reservationService, times(1)).getReservationsByStatus("ACTIVE");
    }

    @Test
    void cancelReservation_Success() throws Exception {
        // Arrange
        testReservationDTO.setStatus(Reservation.ReservationStatus.CANCELLED);
        when(reservationService.cancelReservation(1L)).thenReturn(testReservationDTO);

        // Act & Assert
        mockMvc.perform(patch("/api/reservations/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        verify(reservationService, times(1)).cancelReservation(1L);
    }

    @Test
    void cleanupExpiredReservations_Success() throws Exception {
        // Arrange
        doNothing().when(reservationService).completeExpiredReservations();

        // Act & Assert
        mockMvc.perform(post("/api/reservations/cleanup-expired"))
                .andExpect(status().isOk())
                .andExpect(content().string("Expired reservations cleaned up successfully"));

        verify(reservationService, times(1)).completeExpiredReservations();
    }
}
