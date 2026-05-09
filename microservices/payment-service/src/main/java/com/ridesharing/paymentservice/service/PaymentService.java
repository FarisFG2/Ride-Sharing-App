package com.ridesharing.paymentservice.service;

import com.ridesharing.paymentservice.model.Payment;
import com.ridesharing.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        // Simulate payment processing with external gateway
        // In real implementation, integrate with Stripe, PayPal, etc.
        try {
            // Mock external call - assume 95% success rate
            boolean gatewaySuccess = Math.random() > 0.05;
            if (gatewaySuccess) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
                // Here you would call actual payment gateway API
                System.out.println("Payment processed successfully for ride: " + payment.getRideId());
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
                System.out.println("Payment failed for ride: " + payment.getRideId());
            }
        } catch (Exception e) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            System.out.println("Payment error: " + e.getMessage());
        }
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByRide(Long rideId) {
        return paymentRepository.findByRideId(rideId);
    }

    public List<Payment> getPaymentsByDriver(Long driverId) {
        return paymentRepository.findByDriverId(driverId);
    }

    public Payment refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }
}