package com.backend.revol_store.config;

import com.backend.revol_store.client.TmdbRateLimitHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final TmdbProperties tmdbProperties;
    private final TmdbRateLimitHandler rateLimitHandler;

    @Bean
    public WebClient tmdbWebClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10));

        return WebClient.builder()
                .baseUrl(tmdbProperties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(rateLimitFilter())
                .build();
    }

    private ExchangeFilterFunction rateLimitFilter() {
        return (request, next) -> {
            rateLimitHandler.acquirePermission();
            return next.exchange(request);
        };
    }
}
