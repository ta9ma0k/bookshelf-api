CREATE TABLE books (
  id SERIAL,
  isbn VARCHAR(13) NOT NULL,
  title VARCHAR(255) NOT NULL,
  thumbnail_url VARCHAR(255),
  PRIMARY KEY (id)
);