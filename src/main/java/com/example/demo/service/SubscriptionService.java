package com.example.demo.service;

import com.example.demo.dto.SubscriptionDTO;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidReservationTimeException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(subscriptionDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", subscriptionDTO.getUserId()));
        
        // Validate dates
        if (subscriptionDTO.getStartDate().isAfter(subscriptionDTO.getEndDate())) {
            throw new InvalidReservationTimeException("End date must be after start date");
        }
        
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(subscriptionDTO.getStartDate());
        subscription.setEndDate(subscriptionDTO.getEndDate());
        subscription.setMonthlyFee(subscriptionDTO.getMonthlyFee());
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return mapToDTO(savedSubscription);
    }

    public SubscriptionDTO getSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", id));
        return mapToDTO(subscription);
    }

    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SubscriptionDTO> getActiveSubscriptionByUserId(Long userId) {
        return subscriptionRepository.findActiveSubscriptionByUserId(userId, LocalDate.now())
                .map(this::mapToDTO);
    }

    public SubscriptionDTO renewSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", id));
        
        // Extend subscription by 30 days
        subscription.setEndDate(subscription.getEndDate().plusDays(30));
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        
        Subscription renewedSubscription = subscriptionRepository.save(subscription);
        return mapToDTO(renewedSubscription);
    }

    public SubscriptionDTO cancelSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", id));
        
        subscription.setStatus(Subscription.SubscriptionStatus.CANCELLED);
        Subscription cancelledSubscription = subscriptionRepository.save(subscription);
        return mapToDTO(cancelledSubscription);
    }

    public void expireOldSubscriptions() {
        List<Subscription> expiredSubscriptions = 
                subscriptionRepository.findExpiredSubscriptions(LocalDate.now());
        
        for (Subscription subscription : expiredSubscriptions) {
            subscription.setStatus(Subscription.SubscriptionStatus.EXPIRED);
        }
        
        subscriptionRepository.saveAll(expiredSubscriptions);
    }

    public void deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subscription", id);
        }
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionDTO mapToDTO(Subscription subscription) {
        SubscriptionDTO dto = modelMapper.map(subscription, SubscriptionDTO.class);
        dto.setUserId(subscription.getUser().getId());
        dto.setUserName(subscription.getUser().getName());
        return dto;
    }
}
