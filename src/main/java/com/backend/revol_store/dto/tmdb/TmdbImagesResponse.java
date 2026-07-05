package com.backend.revol_store.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbImagesResponse {

    private Integer id;
    private List<TmdbImage> backdrops;
    private List<TmdbImage> posters;
    private List<TmdbImage> logos;

    @Data
    public static class TmdbImage {
        @JsonProperty("aspect_ratio")
        private Double aspectRatio;
        
        private Integer height;
        
        @JsonProperty("iso_639_1")
        private String iso6391;
        
        @JsonProperty("file_path")
        private String filePath;
        
        @JsonProperty("vote_average")
        private Double voteAverage;
        
        @JsonProperty("vote_count")
        private Integer voteCount;
        
        private Integer width;
    }
}
