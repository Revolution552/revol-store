package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.request.LoginRequest;
import com.backend.revol_store.dto.request.RegisterRequest;
import com.backend.revol_store.dto.response.AuthResponse;
import com.backend.revol_store.dto.response.UserResponse;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.enums.UserRole;
import com.backend.revol_store.exception.BadRequestException;
import com.backend.revol_store.exception.UnauthorizedException;
import com.backend.revol_store.mapper.UserMapper;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.security.JwtTokenProvider;
import com.backend.revol_store.security.UserPrincipal;
import com.backend.revol_store.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        // Update last login
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow();
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        UserResponse userResponse = userMapper.userToUserResponse(user);

        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiresIn(86400000L) // Typically injected from properties
                .build();
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        User user = userMapper.registerRequestToUser(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        
        if (request.getDisplayName() == null || request.getDisplayName().isEmpty()) {
            user.setDisplayName(request.getUsername());
        }

        User savedUser = userRepository.save(user);
        
        // Auto login after registration
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                UserPrincipal.create(savedUser), null, UserPrincipal.create(savedUser).getAuthorities());
                
        String jwt = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .user(userMapper.userToUserResponse(savedUser))
                .expiresIn(86400000L)
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (tokenProvider.validateToken(refreshToken)) {
            Long userId = tokenProvider.getUserIdFromJWT(refreshToken);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("User not found"));
            
            if (user.isBanned()) {
                throw new UnauthorizedException("Account is banned");
            }
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    UserPrincipal.create(user), null, UserPrincipal.create(user).getAuthorities());
                    
            String newAccessToken = tokenProvider.generateToken(authentication);
            String newRefreshToken = tokenProvider.generateRefreshToken(authentication);
            
            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .user(userMapper.userToUserResponse(user))
                    .expiresIn(86400000L)
                    .build();
        }
        throw new UnauthorizedException("Invalid refresh token");
    }
}
