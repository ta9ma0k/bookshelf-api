CREATE TABLE users (
  id integer generated always as identity,
  name VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  PRIMARY KEY (id)
);