package com.example.demo.config;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DataInitializer - Populates the database with sample data for testing
 * 
 * This class creates sample users, parking lots, spots, vehicles, etc.
 * to make it easier to test the application without manually creating data.
 * 
 * To disable this, comment out the @Configuration annotation or delete this file.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            System.out.println("==============================================");
            System.out.println("🚀 Initializing Sample Data...");
            System.out.println("==============================================");

            // Create Users
            User adminUser = createUser("Admin User", "admin@smartparking.com", "admin123", User.UserRole.ADMIN);
            User client1 = createUser("Arber Kosova", "arber@example.com", "password123", User.UserRole.CLIENT);
            User client2 = createUser("Liridona Gashi", "liridona@example.com", "password123", User.UserRole.CLIENT);
            User client3 = createUser("Arta Hoxha", "arta@example.com", "password123", User.UserRole.CLIENT);
            
            System.out.println("✅ Created 4 users (1 admin, 3 clients)");

            // Create Vehicles
            Vehicle vehicle1 = createVehicle("PR-1234-AA", Vehicle.VehicleType.CAR, client1);
            Vehicle vehicle2 = createVehicle("PR-5678-BB", Vehicle.VehicleType.ELECTRIC, client1);
            Vehicle vehicle3 = createVehicle("PR-9012-CC", Vehicle.VehicleType.MOTORCYCLE, client2);
            Vehicle vehicle4 = createVehicle("PR-3456-DD", Vehicle.VehicleType.CAR, client3);
            
            System.out.println("✅ Created 4 vehicles");

            // Create Parking Lots
            ParkingLot lot1 = createParkingLot("Parking Qendra", "Rruga Dëshmorët e Kombit, Prishtinë", 200);
            ParkingLot lot2 = createParkingLot("Parking Grand", "Rr. UÇK, Prishtinë", 150);
            ParkingLot lot3 = createParkingLot("Parking Prishtina Mall", "Autostrada Prishtinë-Ferizaj", 300);
            
            System.out.println("✅ Created 3 parking lots");

            // Create Parking Spots for Lot 1
            createParkingSpot("A-101", ParkingSpot.SpotStatus.FREE, lot1);
            createParkingSpot("A-102", ParkingSpot.SpotStatus.FREE, lot1);
            createParkingSpot("A-103", ParkingSpot.SpotStatus.FREE, lot1);
            createParkingSpot("B-201", ParkingSpot.SpotStatus.FREE, lot1);
            createParkingSpot("B-202", ParkingSpot.SpotStatus.FREE, lot1);
            
            // Create Parking Spots for Lot 2
            createParkingSpot("G-001", ParkingSpot.SpotStatus.FREE, lot2);
            createParkingSpot("G-002", ParkingSpot.SpotStatus.FREE, lot2);
            createParkingSpot("G-003", ParkingSpot.SpotStatus.MAINTENANCE, lot2);
            
            // Create Parking Spots for Lot 3
            createParkingSpot("M-501", ParkingSpot.SpotStatus.FREE, lot3);
            createParkingSpot("M-502", ParkingSpot.SpotStatus.FREE, lot3);
            
            System.out.println("✅ Created 10 parking spots across 3 parking lots");

            // Create Subscriptions
            Subscription sub1 = createSubscription(client1, LocalDate.now(), LocalDate.now().plusDays(30), 50.0);
            Subscription sub2 = createSubscription(client2, LocalDate.now().minusDays(60), LocalDate.now().minusDays(30), 45.0);
            sub2.setStatus(Subscription.SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(sub2);
            
            System.out.println("✅ Created 2 subscriptions (1 active, 1 expired)");

            System.out.println("==============================================");
            System.out.println("✨ Sample Data Initialization Complete!");
            System.out.println("==============================================");
            System.out.println();
            System.out.println("📊 Database Summary:");
            System.out.println("   - Users: " + userRepository.count());
            System.out.println("   - Vehicles: " + vehicleRepository.count());
            System.out.println("   - Parking Lots: " + parkingLotRepository.count());
            System.out.println("   - Parking Spots: " + parkingSpotRepository.count());
            System.out.println("   - Subscriptions: " + subscriptionRepository.count());
            System.out.println();
            System.out.println("🎯 Quick Test Credentials:");
            System.out.println("   Admin: admin@smartparking.com / admin123");
            System.out.println("   Client: arber@example.com / password123");
            System.out.println();
            System.out.println("🌐 Access Points:");
            System.out.println("   - API: http://localhost:8080/api");
            System.out.println("   - H2 Console: http://localhost:8080/h2-console");
            System.out.println("     (JDBC URL: jdbc:h2:mem:smartparkingdb)");
            System.out.println("==============================================");
        };
    }

    private User createUser(String name, String email, String password, User.UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    private Vehicle createVehicle(String licensePlate, Vehicle.VehicleType type, User user) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(licensePlate);
        vehicle.setType(type);
        vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }

    private ParkingLot createParkingLot(String name, String address, Integer totalSpots) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setTotalSpots(totalSpots);
        return parkingLotRepository.save(parkingLot);
    }

    private ParkingSpot createParkingSpot(String spotNumber, ParkingSpot.SpotStatus status, ParkingLot parkingLot) {
        ParkingSpot spot = new ParkingSpot();
        spot.setSpotNumber(spotNumber);
        spot.setStatus(status);
        spot.setParkingLot(parkingLot);
        return parkingSpotRepository.save(spot);
    }

    private Subscription createSubscription(User user, LocalDate startDate, LocalDate endDate, Double monthlyFee) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setMonthlyFee(monthlyFee);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        return subscriptionRepository.save(subscription);
    }
}
