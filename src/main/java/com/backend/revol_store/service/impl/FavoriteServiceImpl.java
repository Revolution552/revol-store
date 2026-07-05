package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.entity.FavoriteItem;
import com.backend.revol_store.entity.Game;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.enums.ContentType;
import com.backend.revol_store.exception.BadRequestException;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.mapper.GameMapper;
import com.backend.revol_store.mapper.MovieMapper;
import com.backend.revol_store.repository.FavoriteItemRepository;
import com.backend.revol_store.repository.GameRepository;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.service.FavoriteService;
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
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteItemRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final GameRepository gameRepository;
    private final MovieMapper movieMapper;
    private final GameMapper gameMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<?> getUserFavorites(Long userId, ContentType contentType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("addedAt").descending());
        
        if (contentType == null) {
            Page<FavoriteItem> items = favoriteRepository.findByUserId(userId, pageable);
            return PagedResponse.from(items);
        }
        
        Page<FavoriteItem> items = favoriteRepository.findByUserIdAndContentType(userId, contentType, pageable);
        
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
    public void addToFavorites(Long userId, Long contentId, ContentType contentType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                
        FavoriteItem item = FavoriteItem.builder()
                .user(user)
                .contentType(contentType)
                .build();
                
        if (contentType == ContentType.MOVIE) {
            if (favoriteRepository.existsByUserIdAndMovieId(userId, contentId)) {
                throw new BadRequestException("Movie already in favorites");
            }
            Movie movie = movieRepository.findById(contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", contentId));
            item.setMovie(movie);
        } else {
            if (favoriteRepository.existsByUserIdAndGameId(userId, contentId)) {
                throw new BadRequestException("Game already in favorites");
            }
            Game game = gameRepository.findById(contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Game", "id", contentId));
            item.setGame(game);
        }
        
        favoriteRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromFavorites(Long userId, Long contentId, ContentType contentType) {
        FavoriteItem item;
        
        if (contentType == ContentType.MOVIE) {
            item = favoriteRepository.findByUserIdAndMovieId(userId, contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("FavoriteItem", "movieId", contentId));
        } else {
            item = favoriteRepository.findByUserIdAndGameId(userId, contentId)
                    .orElseThrow(() -> new ResourceNotFoundException("FavoriteItem", "gameId", contentId));
        }
        
        favoriteRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long contentId, ContentType contentType) {
        if (contentType == ContentType.MOVIE) {
            return favoriteRepository.existsByUserIdAndMovieId(userId, contentId);
        } else {
            return favoriteRepository.existsByUserIdAndGameId(userId, contentId);
        }
    }
}
