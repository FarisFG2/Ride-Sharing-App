package com.ridesharing.rideservice.service;

import com.ridesharing.rideservice.client.NotificationClient;
import com.ridesharing.rideservice.client.PaymentClient;
import com.ridesharing.rideservice.client.UserClient;
import com.ridesharing.rideservice.model.Ride;
import com.ridesharing.rideservice.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private PaymentClient paymentClient;

    public Ride bookRide(Ride ride) {
        ride.setStatus(Ride.RideStatus.REQUESTED);
        Ride savedRide = rideRepository.save(ride);
        
        // Notify Rider
        try {
            Map<String, Object> user = userClient.getUserById(ride.getRiderId());
            String email = (String) user.get("email");
            
            Map<String, String> notificationRequest = new HashMap<>();
            notificationRequest.put("userId", String.valueOf(ride.getRiderId()));
            notificationRequest.put("email", email);
            notificationRequest.put("subject", "Ride Requested");
            notificationRequest.put("message", "Your ride from " + ride.getPickupLocation() + " to " + ride.getDropoffLocation() + " has been requested.");
            
            notificationClient.sendEmail(notificationRequest);
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
        
        return savedRide;
    }

    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id);
    }

    public List<Ride> getRidesByRider(Long riderId) {
        return rideRepository.findByRiderId(riderId);
    }

    public List<Ride> getRidesByDriver(Long driverId) {
        return rideRepository.findByDriverId(driverId);
    }

    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus(Ride.RideStatus.REQUESTED);
    }

    public Ride acceptRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setDriverId(driverId);
        ride.setStatus(Ride.RideStatus.ACCEPTED);
        Ride savedRide = rideRepository.save(ride);
        
        // Notify Rider
        try {
            Map<String, Object> user = userClient.getUserById(ride.getRiderId());
            String email = (String) user.get("email");
            
            Map<String, String> notificationRequest = new HashMap<>();
            notificationRequest.put("userId", String.valueOf(ride.getRiderId()));
            notificationRequest.put("email", email);
            notificationRequest.put("subject", "Ride Accepted");
            notificationRequest.put("message", "Your ride has been accepted by driver " + driverId);
            
            notificationClient.sendEmail(notificationRequest);
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }

        // Trigger Payment on Acceptance
        try {
            Map<String, Object> paymentRequest = new HashMap<>();
            paymentRequest.put("rideId", rideId);
            paymentRequest.put("riderId", ride.getRiderId());
            paymentRequest.put("driverId", driverId);
            paymentRequest.put("amount", ride.getFare());
            paymentRequest.put("paymentMethod", "CREDIT_CARD"); // Default for now
            
            paymentClient.processPayment(paymentRequest);
        } catch (Exception e) {
            System.err.println("Failed to process payment: " + e.getMessage());
        }
        
        return savedRide;
    }

    public Ride updateRideStatus(Long rideId, Ride.RideStatus status) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setStatus(status);
        Ride savedRide = rideRepository.save(ride);
        
        if (status == Ride.RideStatus.COMPLETED) {
            // Notify Rider
            try {
                Map<String, Object> user = userClient.getUserById(ride.getRiderId());
                String email = (String) user.get("email");
                
                Map<String, String> notificationRequest = new HashMap<>();
                notificationRequest.put("userId", String.valueOf(ride.getRiderId()));
                notificationRequest.put("email", email);
                notificationRequest.put("subject", "Ride Completed");
                notificationRequest.put("message", "Your ride has been completed. Fare: $" + ride.getFare());
                
                notificationClient.sendEmail(notificationRequest);
            } catch (Exception e) {
                System.err.println("Failed to send notification: " + e.getMessage());
            }
        }
        
        return savedRide;
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    private double calculateDistance(String pickup, String dropoff) {
        // Simple mock calculation - in real app, use Google Maps API or similar
        return Math.random() * 20 + 5; // Random distance between 5-25 km
    }
}