package com.example.demo.service;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.exception.PaymentFailedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentService paymentService;

    private Reservation testReservation;
    private PaymentDTO testPaymentDTO;
    private Payment testPayment;

    @BeforeEach
    void setUp() {
        // Setup test reservation
        testReservation = new Reservation();
        testReservation.setId(1L);
        testReservation.setPrice(50.0);
        testReservation.setStatus(Reservation.ReservationStatus.ACTIVE);

        // Setup test payment DTO
        testPaymentDTO = new PaymentDTO();
        testPaymentDTO.setReservationId(1L);
        testPaymentDTO.setAmount(50.0);
        testPaymentDTO.setMethod(Payment.PaymentMethod.CREDIT_CARD);

        // Setup test payment
        testPayment = new Payment();
        testPayment.setId(1L);
        testPayment.setAmount(50.0);
        testPayment.setMethod(Payment.PaymentMethod.CREDIT_CARD);
        testPayment.setStatus(Payment.PaymentStatus.COMPLETED);
        testPayment.setReservation(testReservation);
        testPayment.setTimestamp(LocalDateTime.now());
    }

    @Test
    void createPayment_Success() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(paymentRepository.findByReservationId(1L)).thenReturn(Optional.empty());
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(modelMapper.map(any(Payment.class), eq(PaymentDTO.class))).thenReturn(testPaymentDTO);

        // Act
        PaymentDTO result = paymentService.createPayment(testPaymentDTO);

        // Assert
        assertNotNull(result);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void createPayment_ReservationNotFound_ThrowsException() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.createPayment(testPaymentDTO);
        });
    }

    @Test
    void createPayment_PaymentAlreadyExists_ThrowsException() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(paymentRepository.findByReservationId(1L)).thenReturn(Optional.of(testPayment));

        // Act & Assert
        assertThrows(PaymentFailedException.class, () -> {
            paymentService.createPayment(testPaymentDTO);
        });
    }

    @Test
    void createPayment_InvalidAmount_ThrowsException() {
        // Arrange
        testPaymentDTO.setAmount(0.0);

        // Act & Assert
        assertThrows(PaymentFailedException.class, () -> {
            paymentService.createPayment(testPaymentDTO);
        });
    }

    @Test
    void createPayment_AmountMismatch_ThrowsException() {
        // Arrange
        testPaymentDTO.setAmount(100.0); // Different from reservation price (50.0)
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(paymentRepository.findByReservationId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PaymentFailedException.class, () -> {
            paymentService.createPayment(testPaymentDTO);
        });
    }

    @Test
    void getPaymentById_Success() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));
        when(modelMapper.map(testPayment, PaymentDTO.class)).thenReturn(testPaymentDTO);

        // Act
        PaymentDTO result = paymentService.getPaymentById(1L);

        // Assert
        assertNotNull(result);
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void getPaymentById_NotFound_ThrowsException() {
        // Arrange
        when(paymentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.getPaymentById(999L);
        });
    }

    @Test
    void refundPayment_Success() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(modelMapper.map(any(Payment.class), eq(PaymentDTO.class))).thenReturn(testPaymentDTO);

        // Act
        PaymentDTO result = paymentService.refundPayment(1L);

        // Assert
        assertNotNull(result);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void refundPayment_NotCompleted_ThrowsException() {
        // Arrange
        testPayment.setStatus(Payment.PaymentStatus.PENDING);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // Act & Assert
        assertThrows(PaymentFailedException.class, () -> {
            paymentService.refundPayment(1L);
        });
    }

    @Test
    void getTotalRevenue_Success() {
        // Arrange
        when(paymentRepository.getTotalRevenue()).thenReturn(1000.0);

        // Act
        Double result = paymentService.getTotalRevenue();

        // Assert
        assertEquals(1000.0, result);
        verify(paymentRepository, times(1)).getTotalRevenue();
    }

    @Test
    void getTotalRevenue_NoRevenue_ReturnsZero() {
        // Arrange
        when(paymentRepository.getTotalRevenue()).thenReturn(null);

        // Act
        Double result = paymentService.getTotalRevenue();

        // Assert
        assertEquals(0.0, result);
    }
}
