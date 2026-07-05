package com.backend.revol_store.repository;

import com.backend.revol_store.entity.FavoriteItem;
import com.backend.revol_store.enums.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {

    Page<FavoriteItem> findByUserId(Long userId, Pageable pageable);
    
    Page<FavoriteItem> findByUserIdAndContentType(Long userId, ContentType contentType, Pageable pageable);
    
    Optional<FavoriteItem> findByUserIdAndMovieId(Long userId, Long movieId);
    
    Optional<FavoriteItem> findByUserIdAndGameId(Long userId, Long gameId);
    
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    
    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}
