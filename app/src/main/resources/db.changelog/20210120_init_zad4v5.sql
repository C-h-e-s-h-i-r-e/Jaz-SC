CREATE TABLE branch
(
    id   SERIAL,
    name VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id        SERIAL,
    name      VARCHAR NOT NULL,
    branch_id BIGINT  not null,
    PRIMARY KEY (id),
    FOREIGN KEY (branch_id) REFERENCES branch (id)

);

CREATE TABLE auction
(
    id          SERIAL,
    title       VARCHAR       NOT NULL,
    description VARCHAR       NOT NULL,
    price       NUMERIC(9, 2) NOT NULL,
    category_id BIGINT        not null,
    owner_id    BIGINT        not null,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE photo
(
    id         SERIAL,
    link       VARCHAR NOT NULL,
    auction_id BIGINT  NOT NULL,
    place      BIGINT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (auction_id) REFERENCES auction (id)
);

CREATE TABLE parameter
(
    id           SERIAL,
    parameter_name VARCHAR not null,
    PRIMARY KEY (id)
);

CREATE TABLE auction_parameter
(
    auction_id   int not null,
    parameter_id int not null,
    para_value   VARCHAR,
    FOREIGN KEY (auction_id) REFERENCES auction (id),
    FOREIGN KEY (parameter_id) REFERENCES parameter (id),
    CONSTRAINT auction_parameter_id PRIMARY KEY (auction_id, parameter_id)
);