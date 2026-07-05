package com.backend.revol_store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"genres", "cast", "trailers", "images", "reviews"})
@ToString(exclude = {"genres", "cast", "trailers", "images", "reviews"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer tmdbId;

    @Column(length = 20)
    private String imdbId;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 300)
    private String originalTitle;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(length = 500)
    private String tagline;

    @Column(length = 500)
    private String posterPath;

    @Column(length = 500)
    private String backdropPath;

    private LocalDate releaseDate;

    private Integer runtime;

    private Long budget;

    private Long revenue;

    @Column(precision = 10, scale = 3)
    private BigDecimal popularity;

    @Column(precision = 3, scale = 1)
    private BigDecimal voteAverage;

    private Integer voteCount;

    @Column(length = 200)
    private String director;

    @Column(length = 50)
    private String status;

    @Builder.Default
    private boolean isFeatured = false;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String customDescription;

    private LocalDateTime lastSyncedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenre> genres = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<MovieCast> cast = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieTrailer> trailers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieReview> reviews = new ArrayList<>();
}
