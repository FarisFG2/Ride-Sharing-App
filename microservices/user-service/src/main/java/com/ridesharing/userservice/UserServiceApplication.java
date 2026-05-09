package com.ridesharing.userservice;

import com.ridesharing.userservice.model.User;
import com.ridesharing.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@rideshare.com";
            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("Admin123!"));
                admin.setRole(User.Role.ADMIN);
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setPhoneNumber("000-000-0000");
                userRepository.save(admin);
                System.out.println("Seeded admin user: " + adminEmail);
            }
        };
    }
}