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
@Table(name = "movie_cast")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private Integer tmdbPersonId;

    @Column(length = 200)
    private String name;

    @Column(length = 300)
    private String characterName;

    @Column(length = 500)
    private String profilePath;

    private Integer orderIndex;

    @Column(length = 100)
    private String department;

    @Column(length = 200)
    private String job;
}
