package com.backend.revol_store.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbCreditsResponse {

    private Integer id;
    private List<TmdbCast> cast;
    private List<TmdbCrew> crew;

    @Data
    public static class TmdbCast {
        private Integer id;
        private String name;
        @JsonProperty("original_name")
        private String originalName;
        private String character;
        @JsonProperty("profile_path")
        private String profilePath;
        private Integer order;
    }

    @Data
    public static class TmdbCrew {
        private Integer id;
        private String name;
        @JsonProperty("original_name")
        private String originalName;
        private String department;
        private String job;
        @JsonProperty("profile_path")
        private String profilePath;
    }
}
