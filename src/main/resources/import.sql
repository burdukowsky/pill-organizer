INSERT INTO roles (name) VALUES ('ADMIN'), ('VIEWER');
INSERT INTO users (id, username, password) VALUES (1, 'username', '$2a$10$tYp8Q3ujZXSpM.d6xy6jaueLy/XSFVLaj6ms16xU2wnQ24UfT1dBe');
INSERT INTO users_roles (user_id, role_name) VALUES (1, 'ADMIN');