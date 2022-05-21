-- drop table if exists users cascade;
create table if not exists users
(
    id         bigserial primary key,
    username   varchar(30)  not null,
    password   varchar(200) not null,
    email      varchar(50) unique,
    user_role  varchar(20)  not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
insert into users (username, password, email, user_role)
values ('admin', '$2a$12$SpBu8mutcZOxdRJ1uB7K6.gJvohkIh4eG4KbM4YKI.cHt3/Oje3Sq', 'admin@gmail.com', 'ROLE_ADMIN'),
       ('manager', '$2a$12$5LFojdj.ByfyOyO13ziCP.3S.UIezgdPKdiDQriDub1zChxtnZi9u', 'manager@gmail.com', 'ROLE_MANAGER'),
       ('main_manager', '$2a$12$5LFojdj.ByfyOyO13ziCP.3S.UIezgdPKdiDQriDub1zChxtnZi9u', 'main_manager@gmail.com',
        'ROLE_MAIN_MANAGER');

drop table if exists clients cascade;
create table clients
(
    id         bigint generated by default as identity,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    address    varchar(255),
    name       varchar(255),
    passport   varchar(255),
    phone      varchar(255),
    primary key (id)
);


drop table if exists contract cascade;
create table contract
(
    id         bigint generated by default as identity,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    period     integer,
    sum        decimal(19, 2),
    percent    decimal(4, 2),
    status     varchar(30),
    client_id  bigint,
    primary key (id),
    foreign key (client_id) references clients (id)
);

drop table if exists credit_response cascade;
create table credit_response
(
    id          bigint generated by default as identity,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    period      integer,
    sum         decimal(19, 2),
    percent     float,
    status      varchar(30),
    client_id   bigint,
    contract_id bigint,
    primary key (id),
    foreign key (client_id) references clients (id),
    foreign key (contract_id) references contract (id)
);


drop table if exists credit_request cascade;
create table credit_request
(
    id          bigint generated by default as identity,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    period      integer,
    sum         decimal(19, 2),
    status      varchar(30),
    client_id   bigint,
    response_id bigint,
    primary key (id),
    foreign key (client_id) references clients (id)
--     foreign key (response_id) references credit_response (id)
);



INSERT INTO clients (id, name, passport, address, phone)
VALUES (1, 'Иванов', '12-16 454871', 'Москва, ул.Ленина, 12, кв№ 7', '25-25-25'),
       (2, 'Петров', '11-13 666777', 'Астрахань, ул.Боевая, 125, кв№ 71', '+79056981258'),
       (3, 'Сидоров', '2222 666777', 'Москва, пр.Мира, 30, кв № 1', '+792780113888');


INSERT INTO contract (id, period, sum, percent, status, client_id)
VALUES (1, 24, 30000, 15, 'WAITING_SIGNING', 1);

INSERT INTO credit_request(id, period, sum, status, client_id, response_id)
VALUES (1, 12, 100000, 'WAITING', 1, 1),
       (2, 24, 50000, 'CONFIRMED', 1, 2);
--        (3, 24, 600000, 'WAITING', 1,3);

INSERT INTO credit_response(id, period, sum, percent, status, client_id, contract_id)
VALUES (1, 6, 25000, 17, 'REJECTION', 1, 1),
       (2, 24, 30000, 19, 'CONFIRMED', 1, null),
       (3, 24, 100000, 15, 'CONFIRMED', 1, null);
