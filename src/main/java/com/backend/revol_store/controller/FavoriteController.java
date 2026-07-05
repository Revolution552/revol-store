package com.backend.revol_store.controller;

import com.backend.revol_store.dto.response.ApiResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.enums.ContentType;
import com.backend.revol_store.security.UserPrincipal;
import com.backend.revol_store.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<?>>> getUserFavorites(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) ContentType contentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
                favoriteService.getUserFavorites(currentUser.getId(), contentType, page, size),
                "Favorites retrieved"));
    }

    @PostMapping("/{contentType}/{contentId}")
    public ResponseEntity<ApiResponse<Void>> addToFavorites(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable ContentType contentType,
            @PathVariable Long contentId) {
        favoriteService.addToFavorites(currentUser.getId(), contentId, contentType);
        return ResponseEntity.ok(ApiResponse.success("Added to favorites successfully"));
    }

    @DeleteMapping("/{contentType}/{contentId}")
    public ResponseEntity<ApiResponse<Void>> removeFromFavorites(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable ContentType contentType,
            @PathVariable Long contentId) {
        favoriteService.removeFromFavorites(currentUser.getId(), contentId, contentType);
        return ResponseEntity.ok(ApiResponse.success("Removed from favorites successfully"));
    }

    @GetMapping("/{contentType}/{contentId}/check")
    public ResponseEntity<ApiResponse<Boolean>> checkFavoriteStatus(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable ContentType contentType,
            @PathVariable Long contentId) {
        boolean isFavorite = favoriteService.isFavorite(currentUser.getId(), contentId, contentType);
        return ResponseEntity.ok(ApiResponse.success(isFavorite, "Favorite status retrieved"));
    }
}
