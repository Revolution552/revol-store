package com.backend.revol_store.repository;

import com.backend.revol_store.entity.Game;
import com.backend.revol_store.enums.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findByStatus(GameStatus status, Pageable pageable);
    
    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.genres LEFT JOIN FETCH g.platforms WHERE g.id = :id")
    Optional<Game> findByIdWithDetails(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM games WHERE MATCH(title, description, developer, publisher) AGAINST(:query IN BOOLEAN MODE) AND status = :status", nativeQuery = true)
    Page<Game> searchGames(@Param("query") String query, @Param("status") String status, Pageable pageable);
    
    @Query("SELECT g FROM Game g JOIN g.genres gg WHERE gg.genre = :genre AND g.status = :status")
    Page<Game> findByGenreAndStatus(@Param("genre") String genre, @Param("status") GameStatus status, Pageable pageable);
    
    @Query("SELECT g FROM Game g ORDER BY g.totalDownloads DESC LIMIT :limit")
    List<Game> findTopDownloadedGames(@Param("limit") int limit);
}
