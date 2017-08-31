CREATE TABLE users (
    id              BIGSERIAL PRIMARY KEY    NOT NULL,
    full_name       VARCHAR(64)              NOT NULL,
    birth_date      DATE                     NOT NULL,
    username        VARCHAR(64)              NOT NULL,
    email           VARCHAR(64)              NOT NULL,
    hashed_password VARCHAR                  NOT NULL
);

CREATE UNIQUE INDEX users_username_unique_index
    ON users (username);

CREATE UNIQUE INDEX users_email_unique_index
    ON users (email);