package com.backend.revol_store.service;

import com.backend.revol_store.dto.response.MovieDetailResponse;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.MovieMapper;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    
    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void getMovieDetails_WhenMovieExists_ShouldReturnDetails() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        
        MovieDetailResponse response = new MovieDetailResponse();
        response.setId(1L);
        response.setTitle("Test Movie");
        
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.movieToMovieDetailResponse(any(Movie.class))).thenReturn(response);
        
        MovieDetailResponse result = movieService.getMovieDetails(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void getMovieDetails_WhenMovieDoesNotExist_ShouldThrowException() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieDetails(1L));
    }
}
