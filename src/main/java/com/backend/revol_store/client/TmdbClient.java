package com.backend.revol_store.client;

import com.backend.revol_store.config.TmdbProperties;
import com.backend.revol_store.dto.tmdb.TmdbCreditsResponse;
import com.backend.revol_store.dto.tmdb.TmdbImagesResponse;
import com.backend.revol_store.dto.tmdb.TmdbMovieDetailResponse;
import com.backend.revol_store.dto.tmdb.TmdbMovieResponse;
import com.backend.revol_store.dto.tmdb.TmdbVideosResponse;
import com.backend.revol_store.exception.TmdbApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class TmdbClient {

    private final WebClient tmdbWebClient;
    private final TmdbProperties tmdbProperties;

    private static final String API_KEY_PARAM = "api_key";
    
    public TmdbMovieResponse getPopularMovies(int page) {
        return fetch("/movie/popular", queryParam -> queryParam.queryParam("page", page), TmdbMovieResponse.class);
    }

    public TmdbMovieResponse getTopRatedMovies(int page) {
        return fetch("/movie/top_rated", queryParam -> queryParam.queryParam("page", page), TmdbMovieResponse.class);
    }
    
    public TmdbMovieResponse getUpcomingMovies(int page) {
        return fetch("/movie/upcoming", queryParam -> queryParam.queryParam("page", page), TmdbMovieResponse.class);
    }
    
    public TmdbMovieResponse getNowPlayingMovies(int page) {
        return fetch("/movie/now_playing", queryParam -> queryParam.queryParam("page", page), TmdbMovieResponse.class);
    }

    public TmdbMovieResponse searchMovies(String query, int page) {
        return fetch("/search/movie", queryParam -> queryParam
                .queryParam("query", query)
                .queryParam("page", page), TmdbMovieResponse.class);
    }

    public TmdbMovieDetailResponse getMovieDetails(int tmdbId) {
        return fetch("/movie/" + tmdbId, queryParam -> queryParam, TmdbMovieDetailResponse.class);
    }

    public TmdbCreditsResponse getMovieCredits(int tmdbId) {
        return fetch("/movie/" + tmdbId + "/credits", queryParam -> queryParam, TmdbCreditsResponse.class);
    }

    public TmdbVideosResponse getMovieVideos(int tmdbId) {
        return fetch("/movie/" + tmdbId + "/videos", queryParam -> queryParam, TmdbVideosResponse.class);
    }

    public TmdbImagesResponse getMovieImages(int tmdbId) {
        return fetch("/movie/" + tmdbId + "/images", queryParam -> queryParam, TmdbImagesResponse.class);
    }

    private <T> T fetch(String uri, QueryParamBuilder queryParamBuilder, Class<T> responseType) {
        try {
            return tmdbWebClient.get()
                    .uri(uriBuilder -> queryParamBuilder.build(
                            uriBuilder.path(uri)
                                    .queryParam(API_KEY_PARAM, tmdbProperties.getApiKey())
                    ).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> 
                            response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new TmdbApiException("TMDb API client error: " + response.statusCode()))))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> 
                            response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new TmdbApiException("TMDb API server error: " + response.statusCode()))))
                    .bodyToMono(responseType)
                    .block();
        } catch (Exception e) {
            log.error("Error communicating with TMDb API", e);
            throw new TmdbApiException("Failed to fetch data from TMDb API", e);
        }
    }

    @FunctionalInterface
    private interface QueryParamBuilder {
        org.springframework.web.util.UriBuilder build(org.springframework.web.util.UriBuilder builder);
    }
}
