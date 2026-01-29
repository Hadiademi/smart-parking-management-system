package com.example.demo.controller;

import com.example.demo.dto.SubscriptionDTO;
import com.example.demo.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(subscriptionDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable Long id) {
        SubscriptionDTO subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        List<SubscriptionDTO> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUserId(@PathVariable Long userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<SubscriptionDTO> getActiveSubscriptionByUserId(@PathVariable Long userId) {
        Optional<SubscriptionDTO> subscription = subscriptionService.getActiveSubscriptionByUserId(userId);
        return subscription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/renew")
    public ResponseEntity<SubscriptionDTO> renewSubscription(@PathVariable Long id) {
        SubscriptionDTO renewedSubscription = subscriptionService.renewSubscription(id);
        return ResponseEntity.ok(renewedSubscription);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SubscriptionDTO> cancelSubscription(@PathVariable Long id) {
        SubscriptionDTO cancelledSubscription = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(cancelledSubscription);
    }

    @PostMapping("/expire-old")
    public ResponseEntity<String> expireOldSubscriptions() {
        subscriptionService.expireOldSubscriptions();
        return ResponseEntity.ok("Old subscriptions expired successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
