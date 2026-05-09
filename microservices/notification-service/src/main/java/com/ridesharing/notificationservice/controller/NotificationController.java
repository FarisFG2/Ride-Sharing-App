package com.ridesharing.notificationservice.controller;

import com.ridesharing.notificationservice.model.Notification;
import com.ridesharing.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> sendEmail(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        String email = request.get("email");
        String subject = request.get("subject");
        String message = request.get("message");
        return ResponseEntity.ok(notificationService.sendEmail(userId, email, subject, message));
    }

    @PostMapping("/sms")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> sendSMS(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        String phone = request.get("phone");
        String message = request.get("message");
        return ResponseEntity.ok(notificationService.sendSMS(userId, phone, message));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RIDER') or hasRole('DRIVER')")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}