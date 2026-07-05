package com.backend.revol_store.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @NotNull(message = "TMDb ID is required")
    private Integer tmdbId;

    private String customDescription;

    private boolean isFeatured;
}
