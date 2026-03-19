package com.shoestore.service;

import com.shoestore.dto.request.RegisterRequest;
import com.shoestore.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse<String> register(RegisterRequest request);
}
