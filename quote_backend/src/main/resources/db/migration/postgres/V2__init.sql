CREATE TABLE auth_entity
(
    id       UUID NOT NULL,
    login    VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_auth_entity PRIMARY KEY (id)
);

CREATE TABLE quote_entity
(
    id        UUID NOT NULL,
    source_id UUID,
    author_id UUID,
    text      text,
    CONSTRAINT pk_quote_entity PRIMARY KEY (id)
);

CREATE TABLE source_entity
(
    id      UUID NOT NULL,
    type_id UUID,
    name    VARCHAR(255),
    CONSTRAINT pk_source_entity PRIMARY KEY (id)
);

CREATE TABLE source_type_entity
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_source_type_entity PRIMARY KEY (id)
);

CREATE TABLE user_entity
(
    id      UUID NOT NULL,
    name    VARCHAR(255),
    auth_id UUID,
    icon    VARCHAR(255),
    CONSTRAINT pk_user_entity PRIMARY KEY (id)
);

ALTER TABLE source_type_entity
    ADD CONSTRAINT uc_source_type_entity_name UNIQUE (name);

ALTER TABLE quote_entity
    ADD CONSTRAINT FK_QUOTE_ENTITY_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES user_entity (id);

ALTER TABLE quote_entity
    ADD CONSTRAINT FK_QUOTE_ENTITY_ON_SOURCE FOREIGN KEY (source_id) REFERENCES source_entity (id);

ALTER TABLE source_entity
    ADD CONSTRAINT FK_SOURCE_ENTITY_ON_TYPE FOREIGN KEY (type_id) REFERENCES source_type_entity (id);

ALTER TABLE user_entity
    ADD CONSTRAINT FK_USER_ENTITY_ON_AUTH FOREIGN KEY (auth_id) REFERENCES auth_entity (id);