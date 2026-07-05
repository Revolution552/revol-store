package com.backend.revol_store.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "tmdb")
public class TmdbProperties {
    
    @NotBlank
    private String apiKey;
    
    @NotBlank
    private String baseUrl = "https://api.themoviedb.org/3";
    
    @NotBlank
    private String imageBaseUrl = "https://image.tmdb.org/t/p/";
    
    private RateLimit rateLimit = new RateLimit();
    private Sync sync = new Sync();

    @Data
    public static class RateLimit {
        @Min(1)
        private int maxRequestsPerSecond = 40;
    }

    @Data
    public static class Sync {
        private String cron = "0 0 * * * *";
    }
}
