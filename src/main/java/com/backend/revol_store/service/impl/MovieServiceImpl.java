package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.response.MovieDetailResponse;
import com.backend.revol_store.dto.response.MovieResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.MovieMapper;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> getAllMovies(int page, int size, String sort) {
        String[] sortParams = sort != null ? sort.split(",") : new String[]{"popularity", "desc"};
        String sortBy = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        
        List<MovieResponse> content = moviePage.getContent().stream()
                .map(movieMapper::movieToMovieResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(moviePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> getFeaturedMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("popularity").descending());
        Page<Movie> moviePage = movieRepository.findByIsFeaturedTrue(pageable);
        
        List<MovieResponse> content = moviePage.getContent().stream()
                .map(movieMapper::movieToMovieResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(moviePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> getPopularMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("popularity").descending());
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        
        List<MovieResponse> content = moviePage.getContent().stream()
                .map(movieMapper::movieToMovieResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(moviePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDetailResponse getMovieDetails(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        return movieMapper.movieToMovieDetailResponse(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> searchMovies(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.searchMovies(query, pageable);
        
        List<MovieResponse> content = moviePage.getContent().stream()
                .map(movieMapper::movieToMovieResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(moviePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> getMoviesByGenre(String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("popularity").descending());
        Page<Movie> moviePage = movieRepository.findByGenre(genre, pageable);
        
        List<MovieResponse> content = moviePage.getContent().stream()
                .map(movieMapper::movieToMovieResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(moviePage, content);
    }
}
