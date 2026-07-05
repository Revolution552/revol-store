-- V3__seed_games.sql

INSERT INTO games (id, title, developer, publisher, description, cover_image_url, release_date, file_size, download_url, version, status, total_downloads, total_plays, min_requirements, rec_requirements, created_by) VALUES
(1, 'Cyber Odyssey', 'Neon Studios', 'Future Games', 'A cyberpunk action RPG set in the year 2150.', 'https://picsum.photos/seed/CyberOdyssey/400/600', '2023-11-15', '45 GB', 'https://example.com/download/1', '1.0.5', 'PUBLISHED', 1500, 4500, 'OS: Win 10, CPU: i5, RAM: 8GB, GPU: GTX 1060', 'OS: Win 11, CPU: i7, RAM: 16GB, GPU: RTX 3060', 1),
(2, 'Fantasy Realms', 'Magic Arts', 'Epic Publishing', 'Explore vast magical lands and conquer dungeons.', 'https://picsum.photos/seed/FantasyRealms/400/600', '2024-01-20', '60 GB', 'https://example.com/download/2', '2.1.0', 'PUBLISHED', 3200, 8900, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 11, CPU: i7, RAM: 16GB', 1),
(3, 'Galactic Command', 'Starforge Games', 'Cosmic Interactive', 'Build your space empire and conquer the galaxy in this grand strategy game.', 'https://picsum.photos/seed/GalacticCommand/400/600', '2022-05-10', '15 GB', 'https://example.com/download/3', '1.5.2', 'PUBLISHED', 5600, 12000, 'OS: Win 10, CPU: i3, RAM: 4GB', 'OS: Win 10, CPU: i5, RAM: 8GB', 1),
(4, 'Street Rumble 5', 'Fighter Co', 'Arcade Masters', 'The latest installment in the premier fighting game franchise.', 'https://picsum.photos/seed/StreetRumble5/400/600', '2023-08-12', '35 GB', 'https://example.com/download/4', '1.1.0', 'PUBLISHED', 4100, 9500, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 11, CPU: i7, RAM: 16GB', 1),
(5, 'Farm Simulator 2025', 'AgriGames', 'Life Sims', 'Manage your farm, grow crops, and raise livestock.', 'https://picsum.photos/seed/FarmSimulator2025/400/600', '2024-10-01', '25 GB', 'https://example.com/download/5', '1.0.0', 'DRAFT', 0, 0, 'OS: Win 10, CPU: i3, RAM: 8GB', 'OS: Win 10, CPU: i5, RAM: 16GB', 1),
(6, 'Mystic Puzzle', 'Brain Teasers Inc', 'Indie House', 'Solve challenging puzzles to uncover ancient mysteries.', 'https://picsum.photos/seed/MysticPuzzle/400/600', '2021-12-05', '2 GB', 'https://example.com/download/6', '3.0.1', 'PUBLISHED', 8000, 25000, 'OS: Win 7, CPU: Core 2 Duo, RAM: 2GB', 'OS: Win 10, CPU: i3, RAM: 4GB', 1),
(7, 'Velocity Racing', 'Speedster Games', 'Auto Play', 'High octane arcade racing across the globe.', 'https://picsum.photos/seed/VelocityRacing/400/600', '2023-06-22', '50 GB', 'https://example.com/download/7', '1.3.4', 'PUBLISHED', 2800, 7200, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 11, CPU: i7, RAM: 16GB', 1),
(8, 'Zombie Survival', 'Undead Studios', 'Horror Games', 'Survive the apocalypse in this open world survival horror.', 'https://picsum.photos/seed/ZombieSurvival/400/600', '2022-10-31', '40 GB', 'https://example.com/download/8', '2.5.0', 'PUBLISHED', 4500, 11000, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 10, CPU: i7, RAM: 16GB', 1),
(9, 'City Builder City', 'Urban Games', 'Sim Publishing', 'Design and manage your own bustling metropolis.', 'https://picsum.photos/seed/CityBuilderCity/400/600', '2023-03-15', '18 GB', 'https://example.com/download/9', '1.2.1', 'PUBLISHED', 3100, 8400, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 10, CPU: i7, RAM: 16GB', 1),
(10, 'Pixel Hero', 'Retro Dev', 'Indie House', 'A retro-inspired 2D platformer with tight controls.', 'https://picsum.photos/seed/PixelHero/400/600', '2024-02-14', '500 MB', 'https://example.com/download/10', '1.0.2', 'PUBLISHED', 9500, 30000, 'OS: Win 7, CPU: Any, RAM: 1GB', 'OS: Win 10, CPU: Any, RAM: 2GB', 1),
(11, 'Naval Commander', 'Sea Battle Studios', 'Strategy Masters', 'Command a fleet of warships in intense tactical battles.', 'https://picsum.photos/seed/NavalCommander/400/600', '2025-01-01', '30 GB', 'https://example.com/download/11', '0.9.0', 'DRAFT', 0, 0, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 11, CPU: i7, RAM: 16GB', 1),
(12, 'Soccer Pro 2024', 'Sports Interactive', 'EA Games', 'The most realistic soccer simulation game.', 'https://picsum.photos/seed/SoccerPro2024/400/600', '2023-09-20', '45 GB', 'https://example.com/download/12', '1.0.6', 'PUBLISHED', 12000, 45000, 'OS: Win 10, CPU: i5, RAM: 8GB', 'OS: Win 11, CPU: i7, RAM: 16GB', 1);

INSERT INTO game_genres (game_id, genre) VALUES
(1, 'Action'), (1, 'RPG'), (1, 'Sci-Fi'),
(2, 'RPG'), (2, 'Fantasy'), (2, 'Adventure'),
(3, 'Strategy'), (3, 'Sci-Fi'),
(4, 'Action'), (4, 'Fighting'),
(5, 'Simulation'),
(6, 'Puzzle'), (6, 'Indie'),
(7, 'Racing'), (7, 'Sports'),
(8, 'Action'), (8, 'Horror'), (8, 'Survival'),
(9, 'Strategy'), (9, 'Simulation'),
(10, 'Action'), (10, 'Indie'), (10, 'Platformer'),
(11, 'Strategy'), (11, 'Simulation'),
(12, 'Sports'), (12, 'Simulation');

INSERT INTO game_platforms (game_id, platform) VALUES
(1, 'PC'), (1, 'PLAYSTATION'), (1, 'XBOX'),
(2, 'PC'), (2, 'PLAYSTATION'),
(3, 'PC'),
(4, 'PC'), (4, 'PLAYSTATION'), (4, 'XBOX'),
(5, 'PC'), (5, 'NINTENDO'),
(6, 'MOBILE'), (6, 'PC'), (6, 'WEB'),
(7, 'PLAYSTATION'), (7, 'XBOX'), (7, 'PC'),
(8, 'PC'), (8, 'PLAYSTATION'),
(9, 'PC'), (9, 'MOBILE'),
(10, 'NINTENDO'), (10, 'PC'), (10, 'MOBILE'),
(11, 'PC'),
(12, 'PLAYSTATION'), (12, 'XBOX'), (12, 'PC');

INSERT INTO game_screenshots (game_id, image_url, sort_order) VALUES
(1, 'https://picsum.photos/seed/ss1/800/450', 1), (1, 'https://picsum.photos/seed/ss2/800/450', 2),
(2, 'https://picsum.photos/seed/ss3/800/450', 1), (2, 'https://picsum.photos/seed/ss4/800/450', 2),
(3, 'https://picsum.photos/seed/ss5/800/450', 1), (3, 'https://picsum.photos/seed/ss6/800/450', 2),
(4, 'https://picsum.photos/seed/ss7/800/450', 1), (4, 'https://picsum.photos/seed/ss8/800/450', 2),
(5, 'https://picsum.photos/seed/ss9/800/450', 1),
(6, 'https://picsum.photos/seed/ss10/800/450', 1), (6, 'https://picsum.photos/seed/ss11/800/450', 2),
(7, 'https://picsum.photos/seed/ss12/800/450', 1), (7, 'https://picsum.photos/seed/ss13/800/450', 2),
(8, 'https://picsum.photos/seed/ss14/800/450', 1), (8, 'https://picsum.photos/seed/ss15/800/450', 2),
(9, 'https://picsum.photos/seed/ss16/800/450', 1),
(10, 'https://picsum.photos/seed/ss17/800/450', 1), (10, 'https://picsum.photos/seed/ss18/800/450', 2),
(11, 'https://picsum.photos/seed/ss19/800/450', 1),
(12, 'https://picsum.photos/seed/ss20/800/450', 1), (12, 'https://picsum.photos/seed/ss21/800/450', 2);
