package com.backend.revol_store.service.impl;

import com.backend.revol_store.client.TmdbClient;
import com.backend.revol_store.dto.tmdb.TmdbCreditsResponse;
import com.backend.revol_store.dto.tmdb.TmdbImagesResponse;
import com.backend.revol_store.dto.tmdb.TmdbMovieDetailResponse;
import com.backend.revol_store.dto.tmdb.TmdbMovieResponse;
import com.backend.revol_store.dto.tmdb.TmdbVideosResponse;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.entity.MovieCast;
import com.backend.revol_store.entity.MovieGenre;
import com.backend.revol_store.entity.MovieImage;
import com.backend.revol_store.entity.MovieTrailer;
import com.backend.revol_store.mapper.MovieMapper;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.service.TmdbSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TmdbSyncServiceImpl implements TmdbSyncService {

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public void syncPopularMovies(int pages) {
        log.info("Starting sync of popular movies for {} pages", pages);
        for (int i = 1; i <= pages; i++) {
            TmdbMovieResponse response = tmdbClient.getPopularMovies(i);
            processMovies(response);
        }
        log.info("Finished syncing popular movies");
    }

    @Override
    public void syncTopRatedMovies(int pages) {
        log.info("Starting sync of top rated movies for {} pages", pages);
        for (int i = 1; i <= pages; i++) {
            TmdbMovieResponse response = tmdbClient.getTopRatedMovies(i);
            processMovies(response);
        }
        log.info("Finished syncing top rated movies");
    }

    @Override
    public void syncUpcomingMovies(int pages) {
        log.info("Starting sync of upcoming movies for {} pages", pages);
        for (int i = 1; i <= pages; i++) {
            TmdbMovieResponse response = tmdbClient.getUpcomingMovies(i);
            processMovies(response);
        }
        log.info("Finished syncing upcoming movies");
    }

    @Override
    public void syncNowPlayingMovies(int pages) {
        log.info("Starting sync of now playing movies for {} pages", pages);
        for (int i = 1; i <= pages; i++) {
            TmdbMovieResponse response = tmdbClient.getNowPlayingMovies(i);
            processMovies(response);
        }
        log.info("Finished syncing now playing movies");
    }

    @Override
    @Transactional
    public void syncMovieDetails(Integer tmdbId) {
        log.info("Syncing movie details for TMDb ID: {}", tmdbId);
        try {
            TmdbMovieDetailResponse detailResponse = tmdbClient.getMovieDetails(tmdbId);
            TmdbCreditsResponse creditsResponse = tmdbClient.getMovieCredits(tmdbId);
            TmdbVideosResponse videosResponse = tmdbClient.getMovieVideos(tmdbId);
            TmdbImagesResponse imagesResponse = tmdbClient.getMovieImages(tmdbId);

            Optional<Movie> existingOpt = movieRepository.findByTmdbId(tmdbId);
            Movie movie = existingOpt.orElseGet(Movie::new);

            // Update basic details
            updateBasicDetails(movie, detailResponse);
            
            // Clear existing collections
            movie.getGenres().clear();
            movie.getCast().clear();
            movie.getTrailers().clear();
            movie.getImages().clear();

            // Populate genres
            if (detailResponse.getGenres() != null) {
                detailResponse.getGenres().forEach(g -> {
                    movie.getGenres().add(MovieGenre.builder()
                            .movie(movie)
                            .tmdbGenreId(g.getId())
                            .genreName(g.getName())
                            .build());
                });
            }

            // Populate cast (limit to top 15)
            if (creditsResponse.getCast() != null) {
                creditsResponse.getCast().stream().limit(15).forEach(c -> {
                    MovieCast cast = movieMapper.tmdbCastToMovieCast(c);
                    cast.setMovie(movie);
                    movie.getCast().add(cast);
                });
            }
            
            // Set director
            if (creditsResponse.getCrew() != null) {
                creditsResponse.getCrew().stream()
                        .filter(c -> "Director".equals(c.getJob()))
                        .findFirst()
                        .ifPresent(d -> movie.setDirector(d.getName()));
            }

            // Populate trailers (limit to YouTube only)
            if (videosResponse.getResults() != null) {
                videosResponse.getResults().stream()
                        .filter(v -> "YouTube".equals(v.getSite()) && "Trailer".equals(v.getType()))
                        .forEach(v -> {
                            MovieTrailer trailer = movieMapper.tmdbVideoToMovieTrailer(v);
                            trailer.setMovie(movie);
                            movie.getTrailers().add(trailer);
                        });
            }

            // Populate images (limit backdrops)
            if (imagesResponse.getBackdrops() != null) {
                imagesResponse.getBackdrops().stream().limit(5).forEach(i -> {
                    MovieImage image = movieMapper.tmdbBackdropToMovieImage(i);
                    image.setMovie(movie);
                    movie.getImages().add(image);
                });
            }

            movie.setLastSyncedAt(LocalDateTime.now());
            movieRepository.save(movie);
            log.info("Successfully synced movie details for TMDb ID: {}", tmdbId);
        } catch (Exception e) {
            log.error("Error syncing movie details for TMDb ID: {}", tmdbId, e);
        }
    }

    private void processMovies(TmdbMovieResponse response) {
        if (response != null && response.getResults() != null) {
            for (TmdbMovieResponse.TmdbMovie tmdbMovie : response.getResults()) {
                if (tmdbMovie.getId() != null && !movieRepository.existsByTmdbId(tmdbMovie.getId())) {
                    // Only sync details for new movies to avoid heavy API usage during bulk sync
                    // Existing movies can be updated via a separate targeted job
                    syncMovieDetails(tmdbMovie.getId());
                }
            }
        }
    }

    private void updateBasicDetails(Movie movie, TmdbMovieDetailResponse detail) {
        movie.setTmdbId(detail.getId());
        movie.setImdbId(detail.getImdbId());
        movie.setTitle(detail.getTitle());
        movie.setOriginalTitle(detail.getOriginalTitle());
        movie.setOverview(detail.getOverview());
        movie.setTagline(detail.getTagline());
        movie.setPosterPath(detail.getPosterPath());
        movie.setBackdropPath(detail.getBackdropPath());
        movie.setReleaseDate(movieMapper.parseDate(detail.getReleaseDate()));
        movie.setRuntime(detail.getRuntime());
        movie.setBudget(detail.getBudget());
        movie.setRevenue(detail.getRevenue());
        movie.setStatus(detail.getStatus());
        movie.setVoteAverage(detail.getVoteAverage());
        movie.setVoteCount(detail.getVoteCount());
        movie.setPopularity(detail.getPopularity());
    }
}
