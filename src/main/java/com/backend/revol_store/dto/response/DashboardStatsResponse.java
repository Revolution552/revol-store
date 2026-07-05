package com.backend.revol_store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {

    private long totalUsers;
    private long totalMovies;
    private long totalGames;
    private long totalReviews;
    private long activeUsersLast7Days;
    private long newUsersThisMonth;
}
