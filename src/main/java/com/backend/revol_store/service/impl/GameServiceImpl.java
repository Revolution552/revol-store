package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.request.GameRequest;
import com.backend.revol_store.dto.response.GameDetailResponse;
import com.backend.revol_store.dto.response.GameResponse;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.entity.Game;
import com.backend.revol_store.entity.GameGenre;
import com.backend.revol_store.entity.GamePlatform;
import com.backend.revol_store.entity.GameScreenshot;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.enums.GameStatus;
import com.backend.revol_store.enums.Platform;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.GameMapper;
import com.backend.revol_store.repository.GameRepository;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameMapper gameMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getAllPublishedGames(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        Page<Game> gamePage = gameRepository.findByStatus(GameStatus.PUBLISHED, pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getFeaturedGames(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("totalPlays").descending());
        Page<Game> gamePage = gameRepository.findByStatus(GameStatus.PUBLISHED, pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getPopularGames(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("totalDownloads").descending());
        Page<Game> gamePage = gameRepository.findByStatus(GameStatus.PUBLISHED, pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getNewReleases(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        Page<Game> gamePage = gameRepository.findByStatus(GameStatus.PUBLISHED, pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public GameDetailResponse getGameDetails(Long id) {
        Game game = gameRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));
        return gameMapper.gameToGameDetailResponse(game);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> searchGames(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamePage = gameRepository.searchGames(query, GameStatus.PUBLISHED.name(), pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getGamesByGenre(String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        Page<Game> gamePage = gameRepository.findByGenreAndStatus(genre, GameStatus.PUBLISHED, pageable);
        
        List<GameResponse> content = gamePage.getContent().stream()
                .map(gameMapper::gameToGameResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(gamePage, content);
    }

    @Override
    @Transactional
    public GameDetailResponse createGame(GameRequest request, Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));
                
        Game game = gameMapper.gameRequestToGame(request);
        game.setStatus(GameStatus.DRAFT);
        game.setCreatedBy(admin);
        
        populateGameCollections(game, request);
        
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameDetailResponse(savedGame);
    }

    @Override
    @Transactional
    public GameDetailResponse updateGame(Long id, GameRequest request) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));
                
        // Update basic fields
        game.setTitle(request.getTitle());
        game.setDeveloper(request.getDeveloper());
        game.setPublisher(request.getPublisher());
        game.setDescription(request.getDescription());
        game.setCoverImageUrl(request.getCoverImageUrl());
        game.setReleaseDate(request.getReleaseDate());
        game.setFileSize(request.getFileSize());
        game.setDownloadUrl(request.getDownloadUrl());
        game.setVersion(request.getVersion());
        game.setMinRequirements(request.getMinRequirements());
        game.setRecRequirements(request.getRecRequirements());
        
        // Clear collections and re-add
        game.getGenres().clear();
        game.getPlatforms().clear();
        game.getScreenshots().clear();
        
        populateGameCollections(game, request);
        
        Game updatedGame = gameRepository.save(game);
        return gameMapper.gameToGameDetailResponse(updatedGame);
    }

    @Override
    @Transactional
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));
        gameRepository.delete(game);
    }
    
    private void populateGameCollections(Game game, GameRequest request) {
        if (request.getGenres() != null) {
            for (String genreName : request.getGenres()) {
                game.getGenres().add(GameGenre.builder().game(game).genre(genreName).build());
            }
        }
        
        if (request.getPlatforms() != null) {
            for (Platform platform : request.getPlatforms()) {
                game.getPlatforms().add(GamePlatform.builder().game(game).platform(platform).build());
            }
        }
        
        if (request.getScreenshotUrls() != null) {
            int order = 0;
            for (String url : request.getScreenshotUrls()) {
                game.getScreenshots().add(GameScreenshot.builder().game(game).imageUrl(url).sortOrder(order++).build());
            }
        }
    }
}
