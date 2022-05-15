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
    id      integer primary key,
    name    varchar,
    passport text,
    address  text,
    phone   text
);

INSERT INTO clients (id, name, passport, address, phone)
VALUES (1, 'Иванов', '12-16 454871', 'Москва, ул.Ленина, 12, кв№ 7', '25-25-25'),
       (2, 'Петров', '11-13 666777', 'Астрахань, ул.Боевая, 125, кв№ 71', '+79056981258');


create table IF NOT EXISTS creditrequest
(
    id            integer primary key,
    name          text,
    maritalstatus text,
    adress        text,
    phone         text,
    jobdetails    text,
    creditsum     integer,
    pasport       text
);

create table IF NOT EXISTS creditresponse
(
    id        integer primary key,
    idrequest integer,
    name      text,
    period    integer,
    sum       integer,
    status    text,
    pasport   text
);



create table IF NOT EXISTS contract
(
    id      integer primary key,
    name    text,
    pasport text,
    period  integer,
    sum     integer,
    status  text
);

