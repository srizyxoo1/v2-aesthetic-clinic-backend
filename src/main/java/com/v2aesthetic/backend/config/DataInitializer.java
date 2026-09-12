package com.v2aesthetic.backend.config;

import com.v2aesthetic.backend.entity.Admin;
import com.v2aesthetic.backend.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdmin(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminRepository.findByEmail("jayasrix1@gmail.com").isEmpty()) {

                Admin admin = new Admin();

                admin.setEmail("jayasrix1@gmail.com");

                admin.setPassword(
                        passwordEncoder.encode("Sri@21dj")
                );

                adminRepository.save(admin);

                System.out.println("Admin account created successfully!");
            }
        };
    }
}