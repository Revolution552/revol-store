package com.backend.revol_store.service;

import com.backend.revol_store.dto.response.DashboardStatsResponse;
import com.backend.revol_store.dto.response.PagedResponse;

public interface AdminService {
    
    DashboardStatsResponse getDashboardStats();

    PagedResponse<com.backend.revol_store.dto.response.GameResponse> getAllGames(int page, int size);
    
    PagedResponse<com.backend.revol_store.dto.response.MovieResponse> getAllMovies(int page, int size);
    
    PagedResponse<com.backend.revol_store.dto.response.UserResponse> getAllUsers(int page, int size);
    
    void banUser(Long userId);
    
    void unbanUser(Long userId);
    
    void triggerManualSync(String type, int pages);
}
