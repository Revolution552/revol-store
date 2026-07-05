-- V4__seed_admin.sql
-- Extra admin specific configuration or logs

INSERT INTO audit_logs (user_id, action, entity_type, entity_id, details, ip_address) VALUES
(1, 'SYSTEM_INIT', 'SYSTEM', NULL, 'Database initialized and seeded with default data.', '127.0.0.1');
