package com.backend.revol_store.mapper;

import com.backend.revol_store.dto.request.GameRequest;
import com.backend.revol_store.dto.response.GameDetailResponse;
import com.backend.revol_store.dto.response.GameResponse;
import com.backend.revol_store.entity.Game;
import com.backend.revol_store.entity.GameGenre;
import com.backend.revol_store.entity.GamePlatform;
import com.backend.revol_store.entity.GameScreenshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapGameGenres")
    @Mapping(target = "platforms", source = "platforms", qualifiedByName = "mapGamePlatforms")
    GameResponse gameToGameResponse(Game game);

    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapGameGenres")
    @Mapping(target = "platforms", source = "platforms", qualifiedByName = "mapGamePlatforms")
    @Mapping(target = "screenshotUrls", source = "screenshots", qualifiedByName = "mapGameScreenshots")
    GameDetailResponse gameToGameDetailResponse(Game game);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalDownloads", ignore = true)
    @Mapping(target = "totalPlays", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "platforms", ignore = true)
    @Mapping(target = "screenshots", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Game gameRequestToGame(GameRequest request);

    @Named("mapGameGenres")
    default List<String> mapGameGenres(List<GameGenre> genres) {
        if (genres == null) return null;
        return genres.stream()
                .map(GameGenre::getGenre)
                .collect(Collectors.toList());
    }

    @Named("mapGamePlatforms")
    default List<String> mapGamePlatforms(List<GamePlatform> platforms) {
        if (platforms == null) return null;
        return platforms.stream()
                .map(p -> p.getPlatform().name())
                .collect(Collectors.toList());
    }

    @Named("mapGameScreenshots")
    default List<String> mapGameScreenshots(List<GameScreenshot> screenshots) {
        if (screenshots == null) return null;
        return screenshots.stream()
                .map(GameScreenshot::getImageUrl)
                .collect(Collectors.toList());
    }
}
