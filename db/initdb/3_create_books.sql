CREATE TABLE books (
  id SERIAL,
  owner_id integer NOT NULL,
  isbn VARCHAR(13) NOT NULL,
  title VARCHAR(255) NOT NULL,
  thumbnail_url VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) references users(id)
);