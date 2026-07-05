package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.entity.Game;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.entity.WatchlistItem;
import com.backend.revol_store.enums.ContentType;
import com.backend.revol_store.exception.BadRequestException;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.GameMapper;
import com.backend.revol_store.mapper.MovieMapper;
import com.backend.revol_store.repository.GameRepository;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.repository.WatchlistItemRepository;
import com.backend.revol_store.service.WatchlistService;
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
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistItemRepository watchlistRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final GameRepository gameRepository;
    private final MovieMapper movieMapper;
    private final GameMapper gameMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<?> getUserWatchlist(Long userId, ContentType contentType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("addedAt").descending());
        
        if (contentType == null) {
            Page<WatchlistItem> items = watchlistRepository.findByUserId(userId, pageable);
            // Mixed content response, simplified for now to just return the WatchlistItems
            return PagedResponse.from(items);
        }
        
        Page<WatchlistItem> items = watchlistRepository.findByUserIdAndContentType(userId, contentType, pageable);
        
        if (contentType == ContentType.MOVIE) {
            List<?> content = items.getContent().stream()
                    .map(item -> movieMapper.movieToMovieResponse(item.getMovie()))
                    .collect(Collectors.toList());
            return PagedResponse.from(items, (List) content);
        } else {
            List<?> content = items.getContent().stream()
                    .map(item -> gameMapper.gameToGameResponse(item.getGame()))
                    .collect(Collectors.toList());
            return PagedResponse.from(items, (List) content);
        }
    }

    @Override
    @Transactional
    public void addToWatchlist(Long userId, Long contentId, ContentType contentType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                
        WatchlistItem item = WatchlistItem.builder()
                .user(user)
                .contentType(contentType)
                .build();
                
        if (contentType == ContentType.MOVIE) {
            if (watchlistRepository.existsByUserIdAndMovieId(userId, contentId)) {
                throw new BadRequestException("Movie already in watchlist");
            }
            Movie movie = movieRepository.findById(contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", contentId));
            item.setMovie(movie);
        } else {
            if (watchlistRepository.existsByUserIdAndGameId(userId, contentId)) {
                throw new BadRequestException("Game already in watchlist");
            }
            Game game = gameRepository.findById(contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Game", "id", contentId));
            item.setGame(game);
        }
        
        watchlistRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromWatchlist(Long userId, Long contentId, ContentType contentType) {
        WatchlistItem item;
        
        if (contentType == ContentType.MOVIE) {
            item = watchlistRepository.findByUserIdAndMovieId(userId, contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("WatchlistItem", "movieId", contentId));
        } else {
            item = watchlistRepository.findByUserIdAndGameId(userId, contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("WatchlistItem", "gameId", contentId));
        }
        
        watchlistRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInWatchlist(Long userId, Long contentId, ContentType contentType) {
        if (contentType == ContentType.MOVIE) {
            return watchlistRepository.existsByUserIdAndMovieId(userId, contentId);
        } else {
            return watchlistRepository.existsByUserIdAndGameId(userId, contentId);
        }
    }
}
