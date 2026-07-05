package com.backend.revol_store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private Long contentId; // Either gameId or movieId
    private int rating;
    private String reviewText;
    private LocalDateTime createdAt;
    
    private UserSummaryResponse user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSummaryResponse {
        private Long id;
        private String username;
        private String displayName;
        private String avatarUrl;
    }
}
