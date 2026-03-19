package com.shoestore.service.impl;

import com.shoestore.dto.request.RegisterRequest;
import com.shoestore.dto.response.ApiResponse;
import com.shoestore.entity.Role;
import com.shoestore.entity.User;
import com.shoestore.repository.RoleRepository;
import com.shoestore.repository.UserRepository;
import com.shoestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ApiResponse<String> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ApiResponse.error(400, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error(400, "Error: Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .avatar(request.getAvatar())
                .enabled(true)
                .build();

        // Assign default role ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = Role.builder().name("ROLE_USER").build();
                    return roleRepository.save(role);
                });

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ApiResponse.success("User registered successfully!");
    }
}
