CREATE TABLE users (
    id              BIGSERIAL PRIMARY KEY    NOT NULL,
    full_name       CHARACTER VARYING        NOT NULL,
    birth_date      DATE                     NOT NULL,
    username        CHARACTER VARYING        NOT NULL,
    email           CHARACTER VARYING        NOT NULL,
    hashed_password CHARACTER VARYING        NOT NULL,
    UNIQUE (username),
    UNIQUE (email)
)