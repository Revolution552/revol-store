package com.backend.revol_store.client;

import com.backend.revol_store.config.TmdbProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TmdbRateLimitHandler {

    private final Bucket bucket;

    public TmdbRateLimitHandler(TmdbProperties tmdbProperties) {
        int maxRequests = tmdbProperties.getRateLimit().getMaxRequestsPerSecond();
        Bandwidth limit = Bandwidth.classic(maxRequests, Refill.intervally(maxRequests, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public void acquirePermission() {
        try {
            bucket.asBlocking().consume(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Rate limit thread interrupted", e);
        }
    }
}
