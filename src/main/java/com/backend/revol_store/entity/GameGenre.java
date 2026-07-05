package com.backend.revol_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "game_genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GameGenre.GameGenreId.class)
public class GameGenre {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @Id
    @Column(length = 50)
    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameGenreId implements Serializable {
        private Long gameId;
        private String genre;
    }
}
