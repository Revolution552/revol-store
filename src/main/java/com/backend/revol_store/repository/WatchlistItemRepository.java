package com.backend.revol_store.repository;

import com.backend.revol_store.entity.WatchlistItem;
import com.backend.revol_store.enums.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {

    Page<WatchlistItem> findByUserId(Long userId, Pageable pageable);
    
    Page<WatchlistItem> findByUserIdAndContentType(Long userId, ContentType contentType, Pageable pageable);
    
    Optional<WatchlistItem> findByUserIdAndMovieId(Long userId, Long movieId);
    
    Optional<WatchlistItem> findByUserIdAndGameId(Long userId, Long gameId);
    
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    
    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}
