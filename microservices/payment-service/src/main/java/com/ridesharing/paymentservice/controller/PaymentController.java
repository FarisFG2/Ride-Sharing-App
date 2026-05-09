package com.ridesharing.paymentservice.controller;

import com.ridesharing.paymentservice.model.Payment;
import com.ridesharing.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    @PostMapping
    @PreAuthorize("hasRole('RIDER') or hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ride/{rideId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<List<Payment>> getPaymentsByRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(paymentService.getPaymentsByRide(rideId));
    }

    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
    public ResponseEntity<List<Payment>> getPaymentsByDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(paymentService.getPaymentsByDriver(driverId));
    }

    @PostMapping("/{id}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.refundPayment(id));
    }
}