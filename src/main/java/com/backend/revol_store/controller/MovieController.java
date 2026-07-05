package com.backend.revol_store.controller;

import com.backend.revol_store.dto.response.ApiResponse;
import com.backend.revol_store.dto.response.MovieDetailResponse;
import com.backend.revol_store.dto.response.MovieResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "popularity,desc") String sort) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getAllMovies(page, size, sort), "Movies retrieved"));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getFeaturedMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getFeaturedMovies(page, size), "Featured movies retrieved"));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getPopularMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getPopularMovies(page, size), "Popular movies retrieved"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDetailResponse>> getMovieDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMovieDetails(id), "Movie details retrieved"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> searchMovies(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.searchMovies(query, page, size), "Search results retrieved"));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getMoviesByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByGenre(genre, page, size), "Movies by genre retrieved"));
    }
}
