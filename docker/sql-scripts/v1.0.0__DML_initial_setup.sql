-- Insert seed data required by the application

-- Insert roles
INSERT INTO roles (id, authority)
VALUES (1, 'ROLE_CITIZEN'),
       (2, 'ROLE_EMPLOYEE')
ON CONFLICT DO NOTHING;

-- Ensure user id sequence is up-to-date
SELECT setval(pg_get_serial_sequence('"user"', 'id'), COALESCE((SELECT MAX(id) FROM users), 1), true);

-- Insert users
INSERT INTO customer_role_junction (customer_id, role_id)
VALUES (1, 1),
       (2, 2)
ON CONFLICT DO NOTHING;

-- Insert incident categories into table incident_category
INSERT INTO incident_category (id, init_search_radius_in_meters)
VALUES (1, 500),
       (2, 200),
       (3, 1000),
       (4, 3000),
       (5, 5000),
       (6, 1000),
       (7, 3000),
       (8, 1000),
       (9, 1000),
       (10, 200),
       (11, 100)
ON CONFLICT DO NOTHING;

-- Insert incident category names into table incident_category_name
INSERT INTO incident_category_name (category_id, language, name)
VALUES (1, 'en', 'Flood'),
       (1, 'el', 'Πλημμύρα'),
       (2, 'en', 'Fire'),
       (2, 'el', 'Πυρκαγιά'),
       (3, 'en', 'Earthquake'),
       (3, 'el', 'Σεισμός'),
       (4, 'en', 'Tsunami'),
       (4, 'el', 'Τσουνάμι'),
       (5, 'en', 'Hurricane'),
       (5, 'el', 'Τυφώνας'),
       (6, 'en', 'Volcanic Eruption'),
       (6, 'el', 'Έκρηξη Ηφαιστείου'),
       (7, 'en', 'Avalanche'),
       (7, 'el', 'Χιονοστιβάδα'),
       (8, 'en', 'Storm'),
       (8, 'el', 'Καταιγίδα'),
       (9, 'en', 'Criminal Act'),
       (9, 'el', 'Εγκληματική Ενέργεια'),
       (10, 'en', 'Traffic Accident'),
       (10, 'el', 'Τροχαίο Ατύχημα')
ON CONFLICT DO NOTHING;
