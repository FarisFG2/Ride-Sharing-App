package com.ridesharing.rideservice.client;

import com.ridesharing.rideservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    Map<String, Object> getUserById(@PathVariable("id") Long id);
}
