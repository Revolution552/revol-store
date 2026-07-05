package com.backend.revol_store.dto.request;

import com.backend.revol_store.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String developer;
    
    private String publisher;
    
    private String description;
    
    private String coverImageUrl;
    
    private LocalDate releaseDate;
    
    private String fileSize;
    
    private String downloadUrl;
    
    private String version;

    private List<String> genres;

    private List<Platform> platforms;

    @Size(max = 5, message = "Maximum 5 screenshots allowed")
    private List<String> screenshotUrls;

    private String minRequirements;
    
    private String recRequirements;
}
