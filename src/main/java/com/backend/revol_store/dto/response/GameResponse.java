package com.backend.revol_store.dto.response;

import com.backend.revol_store.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {

    private Long id;
    private String title;
    private String developer;
    private String publisher;
    private String coverImageUrl;
    private LocalDate releaseDate;
    private GameStatus status;
    private Long totalDownloads;
    private Long totalPlays;
    
    private List<String> genres;
    private List<String> platforms;
}
