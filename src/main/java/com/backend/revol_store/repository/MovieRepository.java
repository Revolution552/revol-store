package com.backend.revol_store.repository;

import com.backend.revol_store.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTmdbId(Integer tmdbId);
    
    boolean existsByTmdbId(Integer tmdbId);
    
    Page<Movie> findByIsFeaturedTrue(Pageable pageable);
    
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.genres WHERE m.id = :id")
    Optional<Movie> findByIdWithGenres(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM movies WHERE MATCH(title, original_title, overview, tagline, director) AGAINST(:query IN BOOLEAN MODE)", nativeQuery = true)
    Page<Movie> searchMovies(@Param("query") String query, Pageable pageable);
    
    @Query("SELECT m FROM Movie m JOIN m.genres mg WHERE mg.genreName = :genre")
    Page<Movie> findByGenre(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT m FROM Movie m ORDER BY m.popularity DESC LIMIT :limit")
    List<Movie> findTopPopularMovies(@Param("limit") int limit);
}
