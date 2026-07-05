package com.backend.revol_store.service;

import com.backend.revol_store.dto.request.PasswordChangeRequest;
import com.backend.revol_store.dto.response.UserResponse;
import com.backend.revol_store.entity.User;

public interface UserService {
    
    UserResponse getCurrentUser(Long userId);
    
    UserResponse updateProfile(Long userId, String displayName, String avatarUrl);
    
    void changePassword(Long userId, PasswordChangeRequest request);
    
    User getUserEntity(Long userId);
}
