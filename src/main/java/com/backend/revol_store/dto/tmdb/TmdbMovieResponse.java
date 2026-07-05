package com.backend.revol_store.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TmdbMovieResponse {

    private int page;
    
    private List<TmdbMovie> results;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("total_results")
    private int totalResults;

    @Data
    public static class TmdbMovie {
        private Integer id;
        
        private String title;
        
        @JsonProperty("original_title")
        private String originalTitle;
        
        private String overview;
        
        @JsonProperty("poster_path")
        private String posterPath;
        
        @JsonProperty("backdrop_path")
        private String backdropPath;
        
        @JsonProperty("release_date")
        private String releaseDate;
        
        @JsonProperty("genre_ids")
        private List<Integer> genreIds;
        
        @JsonProperty("vote_average")
        private BigDecimal voteAverage;
        
        @JsonProperty("vote_count")
        private Integer voteCount;
        
        private BigDecimal popularity;
        
        private boolean video;
        
        private boolean adult;
    }
}
