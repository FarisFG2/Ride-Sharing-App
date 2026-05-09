package com.ridesharing.paymentservice.repository;

import com.ridesharing.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRideId(Long rideId);
    List<Payment> findByDriverId(Long driverId);
}