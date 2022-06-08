CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

INSERT INTO ROLE (name) VALUES ('user'), ('admin');

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    role_id INT REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS room (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

INSERT INTO ROLE (name) VALUES ('group_1 chat'), ('group_2 chat');

CREATE TABLE IF NOT EXISTS message (
    id SERIAL PRIMARY KEY NOT NULL,
    description TEXT,
    person_id INT REFERENCES person(id)
);