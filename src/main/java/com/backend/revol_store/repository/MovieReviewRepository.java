package com.backend.revol_store.repository;

import com.backend.revol_store.entity.MovieReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {

    Page<MovieReview> findByMovieId(Long movieId, Pageable pageable);
    
    Page<MovieReview> findByUserId(Long userId, Pageable pageable);
    
    Optional<MovieReview> findByMovieIdAndUserId(Long movieId, Long userId);
    
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);
}
