package com.backend.revol_store.repository;

import com.backend.revol_store.entity.GameReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameReviewRepository extends JpaRepository<GameReview, Long> {

    Page<GameReview> findByGameId(Long gameId, Pageable pageable);
    
    Page<GameReview> findByUserId(Long userId, Pageable pageable);
    
    Optional<GameReview> findByGameIdAndUserId(Long gameId, Long userId);
    
    boolean existsByGameIdAndUserId(Long gameId, Long userId);
}
