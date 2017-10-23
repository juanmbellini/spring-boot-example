CREATE TABLE sessions (
    id      BIGSERIAL PRIMARY KEY    NOT NULL,
    user_id INTEGER                  NOT NULL,
    jti     BIGINT                   NOT NULL,
    valid   BOOLEAN DEFAULT TRUE     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX sessions_user_id_jti_unique_index
    ON sessions (user_id, jti);
