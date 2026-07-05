package com.backend.revol_store.service;

import com.backend.revol_store.dto.request.GameRequest;
import com.backend.revol_store.dto.response.GameDetailResponse;
import com.backend.revol_store.dto.response.GameResponse;
import com.backend.revol_store.dto.response.PagedResponse;

public interface GameService {
    
    PagedResponse<GameResponse> getAllPublishedGames(int page, int size);
    
    PagedResponse<GameResponse> getFeaturedGames(int page, int size);
    
    PagedResponse<GameResponse> getPopularGames(int page, int size);
    
    PagedResponse<GameResponse> getNewReleases(int page, int size);
    
    GameDetailResponse getGameDetails(Long id);
    
    PagedResponse<GameResponse> searchGames(String query, int page, int size);
    
    PagedResponse<GameResponse> getGamesByGenre(String genre, int page, int size);
    
    GameDetailResponse createGame(GameRequest request, Long adminId);
    
    GameDetailResponse updateGame(Long id, GameRequest request);
    
    void deleteGame(Long id);
}
