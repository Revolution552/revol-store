package com.backend.revol_store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Long id;
    private Integer tmdbId;
    private String title;
    private String overview;
    private String posterUrl;
    private String backdropUrl;
    private LocalDate releaseDate;
    private BigDecimal voteAverage;
    private Integer voteCount;
    private List<String> genres;
    private BigDecimal popularity;
    private boolean isFeatured;

    public static String buildImageUrl(String imageBaseUrl, String path, String size) {
        if (path == null) return null;
        return imageBaseUrl + size + path;
    }
}
