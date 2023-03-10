--liquibase formatted sql

--changeset Scherbakov Pavel:create-user-table

create table user_entity
(
    id bigint
        constraint user_entity_pkey
            primary key,
    first_name varchar(255),
    last_name varchar(255),
    started_at timestamp,
    user_name varchar(255)
);

alter table user_entity owner to postgres;