package com.backend.revol_store.mapper;

import com.backend.revol_store.dto.response.ReviewResponse;
import com.backend.revol_store.entity.GameReview;
import com.backend.revol_store.entity.MovieReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "contentId", source = "game.id")
    ReviewResponse gameReviewToReviewResponse(GameReview review);

    @Mapping(target = "contentId", source = "movie.id")
    ReviewResponse movieReviewToReviewResponse(MovieReview review);
}
