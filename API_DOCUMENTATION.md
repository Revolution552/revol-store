# Revol Store API Documentation

This document describes the primary REST endpoints for the Revol Store backend. 
A fully interactive OpenAPI UI is available at `http://localhost:8080/swagger-ui/index.html`.

## Base URL
`http://localhost:8080/api`

## Response Format
All API responses follow this standard format:
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "errors": null,
  "timestamp": "2023-10-25T14:30:00",
  "status": 200
}
```

## Authentication Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and receive JWT | No |
| POST | `/api/auth/refresh` | Refresh an expired access token | No |

## User Profile Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/users/me` | Get current user's profile | Yes |
| PUT | `/api/users/me/profile` | Update profile (display name, avatar) | Yes |
| PUT | `/api/users/me/password` | Change user password | Yes |

## Movie Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/movies/featured` | Get featured movies | No |
| GET | `/api/movies/popular` | Get popular movies | No |
| GET | `/api/movies/{id}` | Get detailed movie info | No |
| GET | `/api/movies/search?query=...` | Search movies | No |
| GET | `/api/movies/genre/{genre}` | Get movies by genre | No |

## Game Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/games` | Get all published games | No |
| GET | `/api/games/{id}` | Get detailed game info | No |
| GET | `/api/games/search?query=...` | Search games | No |
| GET | `/api/games/genre/{genre}` | Get games by genre | No |

## Review Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/reviews/movie/{movieId}` | Get movie reviews | No |
| POST | `/api/reviews/movie/{movieId}` | Add a movie review | Yes |
| PUT | `/api/reviews/movie/{reviewId}` | Update a movie review | Yes (Owner) |
| DELETE| `/api/reviews/movie/{reviewId}` | Delete a movie review | Yes (Owner) |
| GET | `/api/reviews/game/{gameId}` | Get game reviews | No |
| POST | `/api/reviews/game/{gameId}` | Add a game review | Yes |
| PUT | `/api/reviews/game/{reviewId}` | Update a game review | Yes (Owner) |
| DELETE| `/api/reviews/game/{reviewId}` | Delete a game review | Yes (Owner) |

## Social Features (Watchlist & Favorites)

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/watchlist` | Get user's watchlist | Yes |
| POST | `/api/watchlist/{type}/{id}` | Add content to watchlist | Yes |
| DELETE| `/api/watchlist/{type}/{id}` | Remove content from watchlist | Yes |
| GET | `/api/favorites` | Get user's favorites | Yes |
| POST | `/api/favorites/{type}/{id}` | Add content to favorites | Yes |
| DELETE| `/api/favorites/{type}/{id}` | Remove content from favorites | Yes |

*Note: `{type}` should be `MOVIE` or `GAME`.*

## Admin Endpoints

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/api/admin/dashboard/stats` | Get basic statistics | Yes (ADMIN) |
| POST | `/api/admin/users/{userId}/ban` | Ban a user | Yes (ADMIN) |
| POST | `/api/admin/users/{userId}/unban` | Unban a user | Yes (ADMIN) |
| POST | `/api/admin/tmdb/sync?type=popular` | Trigger TMDb sync manually | Yes (ADMIN) |
| POST | `/api/admin/games` | Add a new game (Draft) | Yes (ADMIN) |
| PUT | `/api/admin/games/{id}` | Update an existing game | Yes (ADMIN) |
| DELETE| `/api/admin/games/{id}` | Delete a game | Yes (ADMIN) |
