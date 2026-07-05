package com.backend.revol_store.controller;

import com.backend.revol_store.dto.request.ReviewRequest;
import com.backend.revol_store.dto.response.ApiResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.dto.response.ReviewResponse;
import com.backend.revol_store.security.UserPrincipal;
import com.backend.revol_store.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Movie Reviews
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<PagedResponse<ReviewResponse>>> getMovieReviews(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getMovieReviews(movieId, page, size), "Movie reviews retrieved"));
    }

    @PostMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> addMovieReview(
            @PathVariable Long movieId,
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.addMovieReview(movieId, currentUser.getId(), request), "Review added successfully"));
    }

    @PutMapping("/movie/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateMovieReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.updateMovieReview(reviewId, currentUser.getId(), request), "Review updated successfully"));
    }

    @DeleteMapping("/movie/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteMovieReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        reviewService.deleteMovieReview(reviewId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }

    // Game Reviews
    @GetMapping("/game/{gameId}")
    public ResponseEntity<ApiResponse<PagedResponse<ReviewResponse>>> getGameReviews(
            @PathVariable Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getGameReviews(gameId, page, size), "Game reviews retrieved"));
    }

    @PostMapping("/game/{gameId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> addGameReview(
            @PathVariable Long gameId,
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.addGameReview(gameId, currentUser.getId(), request), "Review added successfully"));
    }

    @PutMapping("/game/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateGameReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.updateGameReview(reviewId, currentUser.getId(), request), "Review updated successfully"));
    }

    @DeleteMapping("/game/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteGameReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        reviewService.deleteGameReview(reviewId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }
}
