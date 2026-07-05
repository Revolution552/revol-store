package com.backend.revol_store.entity;

import com.backend.revol_store.enums.Platform;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "game_platforms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GamePlatform.GamePlatformId.class)
public class GamePlatform {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    private Platform platform;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GamePlatformId implements Serializable {
        private Long gameId;
        private Platform platform;
    }
}
