package com.backend.revol_store.service;

import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.enums.ContentType;

public interface FavoriteService {
    
    PagedResponse<?> getUserFavorites(Long userId, ContentType contentType, int page, int size);
    
    void addToFavorites(Long userId, Long contentId, ContentType contentType);
    
    void removeFromFavorites(Long userId, Long contentId, ContentType contentType);
    
    boolean isFavorite(Long userId, Long contentId, ContentType contentType);
}
