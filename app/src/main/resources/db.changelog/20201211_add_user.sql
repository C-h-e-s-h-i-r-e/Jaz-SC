CREATE SEQUENCE hibernate_sequence;
CREATE TABLE users(
    id BIGINT NOT NULL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_username UNIQUE(username)
);

CREATE TABLE user_role(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    u_role    VARCHAR NOT NULL,
    user_id      integer
);