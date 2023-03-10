--liquibase formatted sql

--changeset Scherbakov Pavel:create-user-operation-table

create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to postgres;

create table user_operation_entity
(
    id bigint
        constraint user_operation_entity_pkey
            primary key,
    category varchar(255),
    created_at timestamp,
    sum numeric(19,2),
    user_entity_id bigint
        constraint user_entity_fkey
            references user_entity
);

alter table user_operation_entity owner to postgres;


