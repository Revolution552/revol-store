package com.backend.revol_store.service;

import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.enums.ContentType;

public interface WatchlistService {
    
    PagedResponse<?> getUserWatchlist(Long userId, ContentType contentType, int page, int size);
    
    void addToWatchlist(Long userId, Long contentId, ContentType contentType);
    
    void removeFromWatchlist(Long userId, Long contentId, ContentType contentType);
    
    boolean isInWatchlist(Long userId, Long contentId, ContentType contentType);
}
