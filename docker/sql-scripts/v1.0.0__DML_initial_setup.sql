-- Insert most necessary data for the application to work

-- insert role into table roles
insert into role (id, title)
values (1, 'citizen'),
       (2, 'employee');

-- insert users into table user
-- email: citizen1@dev.com, password: citizen1
-- email: employee1@dev.com, password: employee1
insert into "user" (password, email, first_name, last_name, role_id)
values ('$2a$10$VZhQtTrxB..ZBrXfOK9dF.MPpj6Cr.9Cy52edEjzpbjWAQzSlEf.S', 'citizen1@dev.com', 'citizen', 'one', 1),
       ('$2a$10$z3WUsz7SGCzzIKRpoiDnmeNZzW9dy3/xKTvdkzJU3l7HX5SdPLtOW', 'employee1@dev.com', 'employee', 'one', 2);

-- insert incident categories into table incident_category
insert into incident_category (id, init_search_radius_in_meters)
values (1, 500),
       (2, 200),
       (3, 1000),
       (4, 3000),
       (5, 5000),
       (6, 1000),
       (7, 3000),
       (8, 1000),
       (9, 1000),
       (10, 200),
       (11, 100);

-- insert incident category names into table incident_category_name
insert into incident_category_name (category_id, language, name)
values (1, 'en', 'Flood'),
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
       (10, 'el', 'Τροχαίο Ατύχημα');