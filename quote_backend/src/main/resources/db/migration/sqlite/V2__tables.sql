create table auth_entity
(
    id       blob not null,
    login    varchar(255),
    email    varchar(255),
    password varchar(255),
    primary key (id)
);

create table user_entity
(
    auth_id blob unique
        constraint quote_entity_auth_entity_id_fk references auth_entity,
    id      blob not null,
    name    varchar(255),
    icon    varchar(255),
    primary key (id)
);

create table source_type_entity
(
    id   blob not null,
    name varchar(255) not null unique ,
    primary key (id)
);

create table source_entity
(
    id      blob not null,
    type_id blob
        constraint quote_entity_source_type_entity_id_fk references source_type_entity,
    name    varchar(255),
    primary key (id)
);

create table quote_entity
(
    author_id blob
        constraint quote_entity_user_entity_id_fk references user_entity,
    id        blob not null primary key,
    source_id blob
        constraint quote_entity_source_entity_id_fk references source_entity,
    text      varchar(255)
);
