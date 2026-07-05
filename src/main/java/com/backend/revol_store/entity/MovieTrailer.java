package com.backend.revol_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_trailers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieTrailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(length = 100)
    private String tmdbVideoId;

    @Column(length = 100)
    private String keyName;

    @Column(length = 300)
    private String name;

    @Column(length = 50)
    private String site;

    @Column(length = 50)
    private String type;

    private Boolean official;
}
