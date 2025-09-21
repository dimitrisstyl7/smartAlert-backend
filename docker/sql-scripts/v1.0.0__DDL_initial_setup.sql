-- Enable PostGIS extension
create extension if not exists postgis;

-- create table role
create table role
(
    id    serial primary key,
    title varchar(16) not null unique
);

-- create table user
create table "user"
(
    id         serial primary key,
    -- The maximum length of an email address is 254 characters according to the specification (RFC 5321).
    email      varchar(254) not null unique,
    password   varchar(60)  not null,
    first_name varchar(128) not null,
    last_name  varchar(128) not null,
    role_id    int          not null,
    created_at timestamp    not null default now(),
    foreign key (role_id) references role (id)
);

-- create table incident_category
create table incident_category
(
    id                           serial primary key,
    init_search_radius_in_meters double precision not null
);

-- create table incident_category_name
create table incident_category_name
(
    category_id int          not null,
    language    char(2)      not null,
    name        varchar(128) not null,
    primary key (category_id, language),
    foreign key (category_id) references incident_category (id)
);

-- create enum group_status
create type group_status as enum ('PENDING', 'ACCEPTED', 'DECLINED');

-- create cast for group_status
create cast (varchar AS group_status) with inout as implicit;

-- create table report_group
create table report_group
(
    id                      serial primary key,
    category_id             int                   not null,
    central_point           geometry(Point, 4326) not null,
    status                  group_status          not null default 'PENDING',
    search_radius_in_meters double precision      not null,
    last_updated            timestamp             not null,
    foreign key (category_id) references incident_category (id)
);

-- create table incident_report
create table incident_report
(
    id          serial primary key,
    user_id     int                   not null,
    category_id int                   not null,
    group_id    int                   not null,
    location    geometry(Point, 4326) not null,
    description text,
    image_path  varchar(255),
    created_at  timestamp             not null,
    foreign key (category_id) references incident_category (id),
    foreign key (user_id) references "user" (id),
    foreign key (group_id) references report_group (id)
);