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
public class MovieDetailResponse {

    private Long id;
    private Integer tmdbId;
    private String imdbId;
    private String title;
    private String originalTitle;
    private String overview;
    private String tagline;
    private String posterUrl;
    private String backdropUrl;
    private LocalDate releaseDate;
    private Integer runtime;
    private Long budget;
    private Long revenue;
    private BigDecimal voteAverage;
    private Integer voteCount;
    private BigDecimal popularity;
    private String director;
    private String status;
    private String customDescription;
    private boolean isFeatured;

    private List<String> genres;
    private List<CastResponse> cast;
    private List<TrailerResponse> trailers;
    private List<ImageResponse> images;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CastResponse {
        private String name;
        private String characterName;
        private String profileUrl;
        private Integer orderIndex;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrailerResponse {
        private String name;
        private String keyName;
        private String site;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse {
        private String imageUrl;
        private String type;
    }
}
