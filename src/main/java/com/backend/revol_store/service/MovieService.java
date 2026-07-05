package com.backend.revol_store.service;

import com.backend.revol_store.dto.response.MovieDetailResponse;
import com.backend.revol_store.dto.response.MovieResponse;
import com.backend.revol_store.dto.response.PagedResponse;

public interface MovieService {
    
    PagedResponse<MovieResponse> getAllMovies(int page, int size, String sort);
    
    PagedResponse<MovieResponse> getFeaturedMovies(int page, int size);
    
    PagedResponse<MovieResponse> getPopularMovies(int page, int size);
    
    MovieDetailResponse getMovieDetails(Long id);
    
    PagedResponse<MovieResponse> searchMovies(String query, int page, int size);
    
    PagedResponse<MovieResponse> getMoviesByGenre(String genre, int page, int size);
}
