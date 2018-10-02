INSERT INTO roles (name) VALUES ('ADMIN'), ('EDITOR'), ('VIEWER');
INSERT INTO users (id, username, password) VALUES (1, 'username', '$2a$10$tYp8Q3ujZXSpM.d6xy6jaueLy/XSFVLaj6ms16xU2wnQ24UfT1dBe');
INSERT INTO users_roles (user_id, role_name) VALUES (1, 'ADMIN'), (1, 'EDITOR'), (1, 'VIEWER');
INSERT INTO places (id, name, description) VALUES (1, 'Большая аптечка на шкафу', ''), (2, 'Маленькая аптечка на шкафу', '');
INSERT INTO pills (id, name, description) VALUES (1, 'Диазолин', ''), (2, 'Парацетамол', '');
INSERT INTO pills_places (pill_id, place_id) VALUES (1, 1), (1, 2), (2, 1);