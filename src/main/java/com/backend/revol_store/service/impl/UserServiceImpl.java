package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.request.PasswordChangeRequest;
import com.backend.revol_store.dto.response.UserResponse;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.exception.BadRequestException;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.UserMapper;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getCurrentUser(Long userId) {
        User user = getUserEntity(userId);
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(Long userId, String displayName, String avatarUrl) {
        User user = getUserEntity(userId);
        
        if (displayName != null && !displayName.trim().isEmpty()) {
            user.setDisplayName(displayName);
        }
        
        if (avatarUrl != null && !avatarUrl.trim().isEmpty()) {
            user.setAvatarUrl(avatarUrl);
        }
        
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void changePassword(Long userId, PasswordChangeRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("New passwords do not match");
        }
        
        User user = getUserEntity(userId);
        
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Current password is incorrect");
        }
        
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}
