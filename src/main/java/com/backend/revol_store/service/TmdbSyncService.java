package com.backend.revol_store.service;

public interface TmdbSyncService {
    
    void syncPopularMovies(int pages);
    
    void syncTopRatedMovies(int pages);
    
    void syncUpcomingMovies(int pages);
    
    void syncNowPlayingMovies(int pages);
    
    void syncMovieDetails(Integer tmdbId);
}
