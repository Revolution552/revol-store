package com.backend.revol_store.dto.response;

import com.backend.revol_store.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailResponse {

    private Long id;
    private String title;
    private String developer;
    private String publisher;
    private String description;
    private String coverImageUrl;
    private LocalDate releaseDate;
    private String fileSize;
    private String downloadUrl;
    private String version;
    private GameStatus status;
    private Long totalDownloads;
    private Long totalPlays;
    private String minRequirements;
    private String recRequirements;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> genres;
    private List<String> platforms;
    private List<String> screenshotUrls;
}
