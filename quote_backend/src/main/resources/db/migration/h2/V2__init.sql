create table auth_entity
(
    id uuid not null,
    primary key (id)
);

create table quote_entity
(
    author_id uuid,
    id        uuid not null,
    source_id uuid,
    text      varchar(255),
    primary key (id)
);

create table source_entity
(
    id      uuid not null,
    type_id uuid,
    name    varchar(255),
    primary key (id)
);

create table source_type_entity
(
    id   uuid not null,
    name varchar(255),
    primary key (id)
);

create table user_entity
(
    auth_id uuid unique,
    id      uuid not null,
    name    varchar(255),
    primary key (id)
);


alter table if exists quote_entity
    add constraint FK83k3fsubxrq3n9qainfwfe6dg foreign key (author_id) references user_entity;

alter table if exists quote_entity
    add constraint FKpj0a8kn58w9kq0tqvlg4gk5ba foreign key (source_id) references source_entity;

alter table if exists source_entity
    add constraint FKae1j4arq6b1a72l0ft9kkrks0 foreign key (type_id) references source_type_entity;

alter table if exists user_entity
    add constraint FK1g3uaxqveum8o1ojtjhfxdfux foreign key (auth_id) references auth_entity;