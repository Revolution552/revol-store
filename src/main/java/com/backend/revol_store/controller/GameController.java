package com.backend.revol_store.controller;

import com.backend.revol_store.dto.response.ApiResponse;
import com.backend.revol_store.dto.response.GameDetailResponse;
import com.backend.revol_store.dto.response.GameResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getAllGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getAllPublishedGames(page, size), "Games retrieved"));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getFeaturedGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getFeaturedGames(page, size), "Featured games retrieved"));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getPopularGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getPopularGames(page, size), "Popular games retrieved"));
    }

    @GetMapping("/new-releases")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getNewReleases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getNewReleases(page, size), "New releases retrieved"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GameDetailResponse>> getGameDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getGameDetails(id), "Game details retrieved"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> searchGames(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.searchGames(query, page, size), "Search results retrieved"));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getGamesByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(gameService.getGamesByGenre(genre, page, size), "Games by genre retrieved"));
    }
}
