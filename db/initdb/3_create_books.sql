CREATE TABLE books (
  id integer generated always as identity,
  isbn VARCHAR(13) NOT NULL,
  title VARCHAR NOT NULL,
  thumbnail_url VARCHAR,
  PRIMARY KEY (id)
);