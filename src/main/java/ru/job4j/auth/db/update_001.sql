CREATE TABLE role (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);
INSERT INTO role (name) VALUES ('Admin');
INSERT INTO role (name) VALUES ('Moderator');
INSERT INTO role (name) VALUES ('User');
INSERT INTO role (name) VALUES ('Anonymous');


CREATE TABLE room (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255)
);
INSERT INTO room (name) VALUES ('Junior');
INSERT INTO room (name) VALUES ('Middle');
INSERT INTO room (name) VALUES ('Senior');
INSERT INTO room (name) VALUES ('Sport');
INSERT INTO room (name) VALUES ('Auto');


CREATE TABLE person (
  id SERIAL PRIMARY KEY,
  login VARCHAR(2000),
  password VARCHAR(2000)
);
INSERT INTO person (login, password) VALUES ('parsentev', '123');
INSERT INTO person (login, password) VALUES ('parsentev', '123');
INSERT INTO person (login, password) VALUES ('Jim', '123');
INSERT INTO person (login, password) VALUES ('Jim', '123');
INSERT INTO person (login, password) VALUES ('ivan', '123');


CREATE TABLE message (
  id SERIAL PRIMARY KEY,
  message VARCHAR(255),
  created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  room_id INT REFERENCES room(id),
  person_id INT REFERENCES person(id)
);
INSERT INTO message (message, created, room_id, person_id) VALUES ('Hello world', '2020-07-20', 1, 1);
INSERT INTO message (message, created, room_id, person_id) VALUES ('How to..', '2020-07-19', 1, 1);
INSERT INTO message (message, created, room_id, person_id) VALUES ('Please, help me', '2020-07-18', 2, 1);
INSERT INTO message (message, created, room_id, person_id) VALUES ('Foobar', '2020-09-10', 3, 2);
INSERT INTO message (message, created, room_id, person_id) VALUES ('Test', '2020-09-09', 4, 3);
INSERT INTO message (message, created, room_id, person_id) VALUES ('Happy New Year!', '2020-09-08', 5, 5);

CREATE TABLE person_role (
  person_id INT REFERENCES person(id),
  role_id INT REFERENCES role(id),
  PRIMARY KEY (person_id, role_id)
);

INSERT INTO person_role (person_id, role_id) VALUES (1, 1);
INSERT INTO person_role (person_id, role_id) VALUES (1, 2);
INSERT INTO person_role (person_id, role_id) VALUES (2, 1);
INSERT INTO person_role (person_id, role_id) VALUES (2, 2);