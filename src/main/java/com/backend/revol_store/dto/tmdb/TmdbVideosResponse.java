package com.backend.revol_store.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbVideosResponse {

    private Integer id;
    private List<TmdbVideo> results;

    @Data
    public static class TmdbVideo {
        private String id;
        @JsonProperty("iso_639_1")
        private String iso6391;
        @JsonProperty("iso_3166_1")
        private String iso31661;
        private String name;
        private String key;
        private String site;
        private Integer size;
        private String type;
        private boolean official;
        @JsonProperty("published_at")
        private String publishedAt;
    }
}
