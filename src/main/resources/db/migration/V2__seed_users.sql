-- V2__seed_users.sql

-- Passwords:
-- admin -> admin123 : $2a$10$MzUuSBnCiQzsguLAdECS.Oo1m05woTBIrM54okzBkFzGqfOYlZl..
-- moderator -> mod123 : $2a$10$ZLx1C8qBwNoHJQtkNh22w.3xEDU6Iokj22jM6zTi8N/zGqvOXzObG
-- user -> user123 : $2a$10$Y4ZJZUV5oWM3CPWbWLjUmuIPw9OoD7UIhWqwrsEvt6hf53xbZimDm

INSERT INTO users (username, email, password_hash, display_name, role) VALUES
('admin', 'admin@revolstore.com', '$2a$10$MzUuSBnCiQzsguLAdECS.Oo1m05woTBIrM54okzBkFzGqfOYlZl..', 'Administrator', 'ADMIN'),
('moderator', 'mod@revolstore.com', '$2a$10$ZLx1C8qBwNoHJQtkNh22w.3xEDU6Iokj22jM6zTi8N/zGqvOXzObG', 'Moderator User', 'MODERATOR'),
('user', 'user@revolstore.com', '$2a$10$Y4ZJZUV5oWM3CPWbWLjUmuIPw9OoD7UIhWqwrsEvt6hf53xbZimDm', 'Regular User', 'USER');
