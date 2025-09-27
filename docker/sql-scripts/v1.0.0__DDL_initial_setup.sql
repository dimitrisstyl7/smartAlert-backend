-- Enable PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- create table roles (plural) expected by the JPA entities
CREATE TABLE IF NOT EXISTS roles
(
    id        serial PRIMARY KEY,
    authority varchar(64) NOT NULL UNIQUE
);

-- create table "user" (no role_id column here; roles are mapped via junction table)
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    email      varchar(254) NOT NULL UNIQUE,
    password   varchar(60)  NOT NULL,
    first_name varchar(128) NOT NULL,
    last_name  varchar(128) NOT NULL,
    created_at timestamp      NOT NULL DEFAULT now()
);

-- junction table expected by your @ManyToMany mapping
CREATE TABLE IF NOT EXISTS customer_role_junction
(
    customer_id BIGINT NOT NULL,
    role_id     INT   NOT NULL,
    PRIMARY KEY (customer_id, role_id),
    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- create table incident_category
CREATE TABLE IF NOT EXISTS incident_category
(
    id                           serial PRIMARY KEY,
    init_search_radius_in_meters double precision NOT NULL
);

-- create table incident_category_name
CREATE TABLE IF NOT EXISTS incident_category_name
(
    category_id int          NOT NULL,
    language    char(2)      NOT NULL,
    name        varchar(128) NOT NULL,
    PRIMARY KEY (category_id, language),
    FOREIGN KEY (category_id) REFERENCES incident_category (id) ON DELETE CASCADE
);

-- create enum group_status
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'group_status') THEN
            CREATE TYPE group_status AS ENUM ('PENDING', 'ACCEPTED', 'DECLINED');
        END IF;
    END$$;

-- create cast for group_status (keep original)
CREATE CAST IF NOT EXISTS (varchar AS group_status) WITH INOUT AS IMPLICIT;

-- create table report_group
CREATE TABLE IF NOT EXISTS report_group
(
    id                      serial PRIMARY KEY,
    category_id             int                   NOT NULL,
    central_point           geometry(Point, 4326) NOT NULL,
    status                  group_status          NOT NULL DEFAULT 'PENDING',
    search_radius_in_meters double precision      NOT NULL,
    last_updated            timestamp             NOT NULL,
    FOREIGN KEY (category_id) REFERENCES incident_category (id) ON DELETE CASCADE
);

-- create table incident_report (singular name used by trigger/function)
CREATE TABLE IF NOT EXISTS incident_report
(
    id          serial PRIMARY KEY,
    user_id     int                   NOT NULL,
    category_id int                   NOT NULL,
    group_id    int                   NOT NULL,
    location    geometry(Point, 4326) NOT NULL,
    description text,
    image_path  varchar(255),
    created_at  timestamp             NOT NULL,
    FOREIGN KEY (category_id) REFERENCES incident_category (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES report_group (id) ON DELETE CASCADE
);
