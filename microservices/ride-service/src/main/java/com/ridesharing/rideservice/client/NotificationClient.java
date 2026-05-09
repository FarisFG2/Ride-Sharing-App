package com.ridesharing.rideservice.client;

import com.ridesharing.rideservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "notification-service", configuration = FeignConfig.class)
public interface NotificationClient {
    @PostMapping("/api/notifications/email")
    void sendEmail(@RequestBody Map<String, String> request);

    @PostMapping("/api/notifications/sms")
    void sendSMS(@RequestBody Map<String, String> request);
}
