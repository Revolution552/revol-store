package com.backend.revol_store.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TmdbMovieDetailResponse {

    private Integer id;
    
    @JsonProperty("imdb_id")
    private String imdbId;
    
    private String title;
    
    @JsonProperty("original_title")
    private String originalTitle;
    
    private String overview;
    
    private String tagline;
    
    @JsonProperty("poster_path")
    private String posterPath;
    
    @JsonProperty("backdrop_path")
    private String backdropPath;
    
    @JsonProperty("release_date")
    private String releaseDate;
    
    private Integer runtime;
    
    private Long budget;
    
    private Long revenue;
    
    private String status;
    
    @JsonProperty("vote_average")
    private BigDecimal voteAverage;
    
    @JsonProperty("vote_count")
    private Integer voteCount;
    
    private BigDecimal popularity;
    
    private List<TmdbGenre> genres;

    @Data
    public static class TmdbGenre {
        private Integer id;
        private String name;
    }
}
