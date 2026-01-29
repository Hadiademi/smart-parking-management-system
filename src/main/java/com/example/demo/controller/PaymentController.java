package com.example.demo.controller;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentDTO> getPaymentByReservationId(@PathVariable Long reservationId) {
        PaymentDTO payment = paymentService.getPaymentByReservationId(reservationId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(@PathVariable String status) {
        List<PaymentDTO> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentDTO> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<Map<String, Double>> getTotalRevenue() {
        Double revenue = paymentService.getTotalRevenue();
        Map<String, Double> response = new HashMap<>();
        response.put("totalRevenue", revenue);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<PaymentDTO> refundPayment(@PathVariable Long id) {
        PaymentDTO refundedPayment = paymentService.refundPayment(id);
        return ResponseEntity.ok(refundedPayment);
    }
}
