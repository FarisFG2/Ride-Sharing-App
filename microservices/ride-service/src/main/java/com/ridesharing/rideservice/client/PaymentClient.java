package com.ridesharing.rideservice.client;

import com.ridesharing.rideservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "payment-service", configuration = FeignConfig.class)
public interface PaymentClient {
    @PostMapping("/api/payments/process")
    Map<String, Object> processPayment(@RequestBody Map<String, Object> payment);
}
