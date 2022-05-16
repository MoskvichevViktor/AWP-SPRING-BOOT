-- CREATE DATABASE awp OWNER postgres ENCODING 'UTF8';

-- DROP DATABASE IF EXISTS awp;
-- CREATE SCHEMA awp;


create table if not exists users
(
    id         bigserial primary key,
    username   varchar(30)  not null,
    password   varchar(200) not null,
    email      varchar(50) unique,
    user_role  varchar(20) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
insert into users (username, password, email, user_role)
values ('admin', '$2a$12$SpBu8mutcZOxdRJ1uB7K6.gJvohkIh4eG4KbM4YKI.cHt3/Oje3Sq', 'admin@gmail.com', 'ROLE_ADMIN'),
       ('manager', '$2a$12$5LFojdj.ByfyOyO13ziCP.3S.UIezgdPKdiDQriDub1zChxtnZi9u', 'manager@gmail.com', 'ROLE_MANAGER'),
       ('main_manager', '$2a$12$5LFojdj.ByfyOyO13ziCP.3S.UIezgdPKdiDQriDub1zChxtnZi9u', 'main_manager@gmail.com', 'ROLE_MAIN_MANAGER');

create table IF NOT EXISTS clients
(
    id         integer primary key,
    name       varchar,
    passport   text,
    address    text,
    phone      text,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table IF NOT EXISTS credit_request
(
    id         integer primary key,
    period     integer,
    sum        money,
    status     text,
    clients    bigint,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp

);


INSERT INTO clients (id, name, passport, address, phone)
VALUES (1, 'Иванов', '12-16 454871', 'Москва, ул.Ленина, 12, кв№ 7', '25-25-25'),
       (2, 'Петров', '11-13 666777', 'Астрахань, ул.Боевая, 125, кв№ 71', '+79056981258');


INSERT INTO credit_request(id, period, sum, status, clients)
VALUES (1, 12, 100000, 'created', 1),
       (2, 24, 50000, 'waiting', 2),
       (3, 24, 600000, 'waiting', 2),
       (4, 6, 25000, 'rejection', 3),
       (5, 18, 900000, 'confirmed', 4),
       (6, 18, 999999, 'rejection', 4);


create table IF NOT EXISTS contracts
(
    id         integer primary key,
    period     integer,
    sum        money,
    status     text,
    clients    bigint,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO contracts (id, period, sum, status, clients)
VALUES (1, 24, 66000, 'active', 1),
       (2, 24, 100250, 'completed', 1),
       (3, 24, 100250, 'active', 2),
       (4, 24, 40000, 'active', 3),
       (5, 24, 60000, 'completed', 2);





