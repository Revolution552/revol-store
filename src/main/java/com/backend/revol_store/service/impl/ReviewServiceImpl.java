package com.backend.revol_store.service.impl;

import com.backend.revol_store.dto.request.ReviewRequest;
import com.backend.revol_store.dto.response.PagedResponse;
import com.backend.revol_store.dto.response.ReviewResponse;
import com.backend.revol_store.entity.Game;
import com.backend.revol_store.entity.GameReview;
import com.backend.revol_store.entity.Movie;
import com.backend.revol_store.entity.MovieReview;
import com.backend.revol_store.entity.User;
import com.backend.revol_store.exception.BadRequestException;
import com.backend.revol_store.exception.ResourceNotFoundException;
import com.backend.revol_store.exception.UnauthorizedException;
import com.backend.revol_store.mapper.ReviewMapper;
import com.backend.revol_store.repository.GameRepository;
import com.backend.revol_store.repository.GameReviewRepository;
import com.backend.revol_store.repository.MovieRepository;
import com.backend.revol_store.repository.MovieReviewRepository;
import com.backend.revol_store.repository.UserRepository;
import com.backend.revol_store.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final MovieReviewRepository movieReviewRepository;
    private final GameReviewRepository gameReviewRepository;
    private final MovieRepository movieRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ReviewResponse> getMovieReviews(Long movieId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<MovieReview> reviewPage = movieReviewRepository.findByMovieId(movieId, pageable);
        
        List<ReviewResponse> content = reviewPage.getContent().stream()
                .map(reviewMapper::movieReviewToReviewResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(reviewPage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ReviewResponse> getGameReviews(Long gameId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<GameReview> reviewPage = gameReviewRepository.findByGameId(gameId, pageable);
        
        List<ReviewResponse> content = reviewPage.getContent().stream()
                .map(reviewMapper::gameReviewToReviewResponse)
                .collect(Collectors.toList());
                
        return PagedResponse.from(reviewPage, content);
    }

    @Override
    @Transactional
    public ReviewResponse addMovieReview(Long movieId, Long userId, ReviewRequest request) {
        if (movieReviewRepository.existsByMovieIdAndUserId(movieId, userId)) {
            throw new BadRequestException("You have already reviewed this movie");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        MovieReview review = MovieReview.builder()
                .movie(movie)
                .user(user)
                .rating(request.getRating())
                .reviewText(request.getReviewText())
                .build();

        return reviewMapper.movieReviewToReviewResponse(movieReviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse addGameReview(Long gameId, Long userId, ReviewRequest request) {
        if (gameReviewRepository.existsByGameIdAndUserId(gameId, userId)) {
            throw new BadRequestException("You have already reviewed this game");
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", gameId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        GameReview review = GameReview.builder()
                .game(game)
                .user(user)
                .rating(request.getRating())
                .reviewText(request.getReviewText())
                .build();

        return reviewMapper.gameReviewToReviewResponse(gameReviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse updateMovieReview(Long reviewId, Long userId, ReviewRequest request) {
        MovieReview review = movieReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieReview", "id", reviewId));
                
        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only update your own reviews");
        }

        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());

        return reviewMapper.movieReviewToReviewResponse(movieReviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse updateGameReview(Long reviewId, Long userId, ReviewRequest request) {
        GameReview review = gameReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("GameReview", "id", reviewId));
                
        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only update your own reviews");
        }

        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());

        return reviewMapper.gameReviewToReviewResponse(gameReviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deleteMovieReview(Long reviewId, Long userId) {
        MovieReview review = movieReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieReview", "id", reviewId));
                
        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only delete your own reviews");
        }

        movieReviewRepository.delete(review);
    }

    @Override
    @Transactional
    public void deleteGameReview(Long reviewId, Long userId) {
        GameReview review = gameReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("GameReview", "id", reviewId));
                
        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only delete your own reviews");
        }

        gameReviewRepository.delete(review);
    }
}
