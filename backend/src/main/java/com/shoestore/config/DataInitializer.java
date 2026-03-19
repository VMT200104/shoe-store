package com.shoestore.config;

import com.shoestore.entity.Role;
import com.shoestore.entity.User;
import com.shoestore.repository.RoleRepository;
import com.shoestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed Roles
        Role userRole = seedRole("ROLE_USER");
        Role adminRole = seedRole("ROLE_ADMIN");

        // Seed Default Admin User
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@shoestore.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("System")
                    .lastName("Admin")
                    .phone("0000000000")
                    .address("Admin Office")
                    .avatar("https://api.dicebear.com/7.x/avataaars/svg?seed=admin")
                    .enabled(true)
                    .roles(Set.of(adminRole, userRole))
                    .build();
            userRepository.save(admin);
            System.out.println("Default admin user created: admin / admin123");
        }
    }

    private Role seedRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role role = Role.builder().name(roleName).build();
                    return roleRepository.save(role);
                });
    }
}
