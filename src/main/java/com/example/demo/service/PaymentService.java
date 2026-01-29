package com.example.demo.service;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Reservation;
import com.example.demo.exception.PaymentFailedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        // Get reservation
        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", paymentDTO.getReservationId()));
        
        // Check if payment already exists for this reservation
        if (paymentRepository.findByReservationId(reservation.getId()).isPresent()) {
            throw new PaymentFailedException("Payment already exists for this reservation");
        }
        
        // Validate amount
        if (paymentDTO.getAmount() <= 0) {
            throw new PaymentFailedException("Payment amount must be greater than 0");
        }
        
        // Verify amount matches reservation price
        if (!paymentDTO.getAmount().equals(reservation.getPrice())) {
            throw new PaymentFailedException(
                String.format("Payment amount (%.2f) does not match reservation price (%.2f)", 
                    paymentDTO.getAmount(), reservation.getPrice())
            );
        }
        
        // Create payment
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setMethod(paymentDTO.getMethod());
        payment.setReservation(reservation);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setTimestamp(LocalDateTime.now());
        
        // Process payment (simulate payment processing)
        boolean paymentSuccessful = processPayment(payment);
        
        if (paymentSuccessful) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            throw new PaymentFailedException("Payment processing failed. Please try again.");
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        return modelMapper.map(savedPayment, PaymentDTO.class);
    }

    /**
     * Simulate payment processing
     * In a real application, this would integrate with a payment gateway
     */
    private boolean processPayment(Payment payment) {
        // Simulate processing delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate 95% success rate
        return Math.random() < 0.95;
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentByReservationId(Long reservationId) {
        Payment payment = paymentRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for reservation: " + reservationId));
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public List<PaymentDTO> getPaymentsByStatus(String status) {
        Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.valueOf(status.toUpperCase());
        return paymentRepository.findByStatus(paymentStatus).stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByDateRange(startDate, endDate).stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    public Double getTotalRevenue() {
        Double revenue = paymentRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    public PaymentDTO refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        
        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new PaymentFailedException("Only completed payments can be refunded");
        }
        
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        Payment updatedPayment = paymentRepository.save(payment);
        return modelMapper.map(updatedPayment, PaymentDTO.class);
    }
}
