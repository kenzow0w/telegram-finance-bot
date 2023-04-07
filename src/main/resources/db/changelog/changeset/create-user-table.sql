--liquibase formatted sql

--changeset vladokus: create-user-table

create table user
(
    chat_id     bigint
        constraint user_entity_pkey,
            primary key,
    first_name  varchar(255),
    last_name   varchar(255),
    user_name   varchar(255),
    started_at  timestamp,
    balance     number,
    expenses_id bigint
        constraint id
            foreign key,
    incomes_id  bigint
        constraint id
            foreign key,
);

alter table user owner to postgres;



