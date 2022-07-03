CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS room (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role_id INT REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS message (
    id SERIAL PRIMARY KEY NOT NULL,
    description TEXT,
    person_id INT REFERENCES person(id),
    room_id INT REFERENCES room(id)
);

INSERT INTO role (name) VALUES ('user'), ('admin');
INSERT INTO room (name) VALUES ('group_1 chat'), ('group_2 chat');

INSERT INTO person (name, email, password, role_id) VALUES ('Sergei', 'Serega@com', '123', 1);
INSERT INTO person (name, email, password, role_id) VALUES ('SergeiAdmin', 'Admin@com', '456', 2);

INSERT INTO message(description, person_id, room_id) VALUES ('Hello Admin!', 1, 1);
INSERT INTO message(description, person_id, room_id) VALUES ('Hi Serega!', 2, 1);
INSERT INTO message(description, person_id, room_id) VALUES ('Hello world!', 2, 2);