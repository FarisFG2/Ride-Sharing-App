package com.ridesharing.rideservice.controller;

import com.ridesharing.rideservice.model.Ride;
import com.ridesharing.rideservice.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping
    @PreAuthorize("hasRole('RIDER')")
    public ResponseEntity<Ride> bookRide(@RequestBody Ride ride) {
        return ResponseEntity.ok(rideService.bookRide(ride));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long id) {
        return rideService.getRideById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rider/{riderId}")
    @PreAuthorize("hasRole('RIDER') or hasRole('ADMIN')")
    public ResponseEntity<List<Ride>> getRidesByRider(@PathVariable Long riderId) {
        return ResponseEntity.ok(rideService.getRidesByRider(riderId));
    }

    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<List<Ride>> getRidesByDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(rideService.getRidesByDriver(driverId));
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<Ride>> getAvailableRides() {
        return ResponseEntity.ok(rideService.getAvailableRides());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Ride> acceptRide(@PathVariable Long id, @RequestParam Long driverId) {
        return ResponseEntity.ok(rideService.acceptRide(id, driverId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<Ride> updateRideStatus(@PathVariable Long id, @RequestParam Ride.RideStatus status) {
        return ResponseEntity.ok(rideService.updateRideStatus(id, status));
    }
}