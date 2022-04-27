-- CREATE DATABASE awp;
-- DROP SCHEMA IF EXISTS awp;
-- CREATE SCHEMA awp;


create table IF NOT EXISTS person
(
    id      integer primary key,
    name    varchar,
    pasport text,
    adress  text,
    phone   text
);




-- alter table person
--     owner to postgres;

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

-- alter table creditrequest
--     owner to postgres;

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

-- alter table creditresponse
--     owner to postgres;

create table IF NOT EXISTS contract
(
    id      integer primary key,
    name    text,
    pasport text,
    period  integer,
    sum     integer,
    status  text
);

-- alter table contract
--     owner to postgres;

INSERT INTO person (id, name, pasport, adress, phone)
VALUES (1, 'Иванов', '12-16 454871', 'Москва, ул.Ленина, 12, кв№ 7', '25-25-25'),
       (2, 'Петров', '11-13 666777', 'Астрахань, ул.Боевая, 125, кв№ 71', '+79056981258');