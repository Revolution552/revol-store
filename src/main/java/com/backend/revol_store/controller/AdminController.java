package com.backend.revol_store.controller;

import com.backend.revol_store.dto.request.GameRequest;
import com.backend.revol_store.dto.response.ApiResponse;
import com.backend.revol_store.dto.response.DashboardStatsResponse;
import com.backend.revol_store.dto.response.GameDetailResponse;
import com.backend.revol_store.dto.response.GameResponse;
import com.backend.revol_store.dto.response.MovieResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.dto.response.UserResponse;
import com.backend.revol_store.security.UserPrincipal;
import com.backend.revol_store.service.AdminService;
import com.backend.revol_store.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final GameService gameService;

    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse<DashboardStatsResponse>> getDashboardStats() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getDashboardStats(), "Dashboard stats retrieved"));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllUsers(page, size), "All users retrieved"));
    }

    @GetMapping("/games")
    public ResponseEntity<ApiResponse<PagedResponse<GameResponse>>> getAllGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllGames(page, size), "All games retrieved"));
    }

    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllMovies(page, size), "All movies retrieved"));
    }

    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<ApiResponse<Void>> banUser(@PathVariable Long userId) {
        adminService.banUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User banned successfully"));
    }

    @PostMapping("/users/{userId}/unban")
    public ResponseEntity<ApiResponse<Void>> unbanUser(@PathVariable Long userId) {
        adminService.unbanUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User unbanned successfully"));
    }

    @PostMapping("/tmdb/sync")
    public ResponseEntity<ApiResponse<Void>> triggerTmdbSync(
            @RequestParam String type,
            @RequestParam(defaultValue = "1") int pages) {
        adminService.triggerManualSync(type, pages);
        return ResponseEntity.ok(ApiResponse.success("TMDb sync triggered successfully for type: " + type));
    }
    
    // Game Management
    @PostMapping("/games")
    public ResponseEntity<ApiResponse<GameDetailResponse>> createGame(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody GameRequest request) {
        return ResponseEntity.ok(ApiResponse.success(gameService.createGame(request, currentUser.getId()), "Game created successfully"));
    }
    
    @PutMapping("/games/{id}")
    public ResponseEntity<ApiResponse<GameDetailResponse>> updateGame(
            @PathVariable Long id,
            @Valid @RequestBody GameRequest request) {
        return ResponseEntity.ok(ApiResponse.success(gameService.updateGame(id, request), "Game updated successfully"));
    }
    
    @DeleteMapping("/games/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok(ApiResponse.success("Game deleted successfully"));
    }
}
