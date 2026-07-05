package com.backend.revol_store.service;

import com.backend.revol_store.dto.request.LoginRequest;
import com.backend.revol_store.dto.request.RegisterRequest;
import com.backend.revol_store.dto.response.AuthResponse;

public interface AuthService {
    
    AuthResponse login(LoginRequest request);
    
    AuthResponse register(RegisterRequest request);
    
    AuthResponse refreshToken(String refreshToken);
}
