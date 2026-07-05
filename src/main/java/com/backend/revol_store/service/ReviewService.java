package com.backend.revol_store.service;

import com.backend.revol_store.dto.request.ReviewRequest;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.dto.response.ReviewResponse;

public interface ReviewService {
    
    PagedResponse<ReviewResponse> getMovieReviews(Long movieId, int page, int size);
    
    PagedResponse<ReviewResponse> getGameReviews(Long gameId, int page, int size);
    
    ReviewResponse addMovieReview(Long movieId, Long userId, ReviewRequest request);
    
    ReviewResponse addGameReview(Long gameId, Long userId, ReviewRequest request);
    
    ReviewResponse updateMovieReview(Long reviewId, Long userId, ReviewRequest request);
    
    ReviewResponse updateGameReview(Long reviewId, Long userId, ReviewRequest request);
    
    void deleteMovieReview(Long reviewId, Long userId);
    
    void deleteGameReview(Long reviewId, Long userId);
}
