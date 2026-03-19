package com.shoestore.service;

import com.shoestore.dto.request.LoginRequest;
import com.shoestore.dto.request.RegisterRequest;
import com.shoestore.dto.response.ApiResponse;
import com.shoestore.dto.response.JwtResponse;

public interface AuthService {
    ApiResponse<String> register(RegisterRequest request);
    ApiResponse<JwtResponse> login(LoginRequest request);
}
