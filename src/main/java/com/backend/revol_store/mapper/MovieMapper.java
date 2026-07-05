package com.backend.revol_store.mapper;

import com.backend.revol_store.dto.response.MovieDetailResponse;
import com.backend.revol_store.dto.response.MovieResponse;
import com.backend.revol_store.dto.tmdb.TmdbCreditsResponse;
import com.backend.revol_store.dto.tmdb.TmdbImagesResponse;
import com.backend.revol_store.dto.tmdb.TmdbMovieDetailResponse;
import com.backend.revol_store.dto.tmdb.TmdbVideosResponse;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.entity.MovieCast;
import com.backend.revol_store.entity.MovieGenre;
import com.backend.revol_store.entity.MovieImage;
import com.backend.revol_store.entity.MovieTrailer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "posterUrl", source = "posterPath")
    @Mapping(target = "backdropUrl", source = "backdropPath")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapMovieGenres")
    @Mapping(target = "isFeatured", source = "featured")
    MovieResponse movieToMovieResponse(Movie movie);

    @Mapping(target = "posterUrl", source = "posterPath")
    @Mapping(target = "backdropUrl", source = "backdropPath")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapMovieGenres")
    @Mapping(target = "isFeatured", source = "featured")
    MovieDetailResponse movieToMovieDetailResponse(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tmdbId", source = "id")
    @Mapping(target = "voteAverage", source = "voteAverage")
    @Mapping(target = "voteCount", source = "voteCount")
    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "parseDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "cast", ignore = true)
    @Mapping(target = "trailers", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "isFeatured", ignore = true)
    @Mapping(target = "customDescription", ignore = true)
    @Mapping(target = "lastSyncedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "director", ignore = true)
    Movie tmdbMovieDetailToMovie(TmdbMovieDetailResponse tmdbMovie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "tmdbPersonId", source = "id")
    @Mapping(target = "characterName", source = "character")
    @Mapping(target = "orderIndex", source = "order")
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "job", ignore = true)
    MovieCast tmdbCastToMovieCast(TmdbCreditsResponse.TmdbCast tmdbCast);

    @Mapping(target = "profileUrl", source = "profilePath")
    MovieDetailResponse.CastResponse movieCastToCastResponse(MovieCast movieCast);

    @Mapping(target = "imageUrl", source = "filePath")
    @Mapping(target = "type", source = "imageType")
    MovieDetailResponse.ImageResponse movieImageToImageResponse(MovieImage movieImage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "tmdbVideoId", source = "id")
    @Mapping(target = "keyName", source = "key")
    MovieTrailer tmdbVideoToMovieTrailer(TmdbVideosResponse.TmdbVideo tmdbVideo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "imageType", constant = "BACKDROP")
    MovieImage tmdbBackdropToMovieImage(TmdbImagesResponse.TmdbImage tmdbImage);

    @Named("mapMovieGenres")
    default List<String> mapMovieGenres(List<MovieGenre> genres) {
        if (genres == null) return null;
        return genres.stream()
                .map(MovieGenre::getGenreName)
                .collect(Collectors.toList());
    }

    @Named("parseDate")
    default LocalDate parseDate(String date) {
        if (date == null || date.isEmpty()) return null;
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
