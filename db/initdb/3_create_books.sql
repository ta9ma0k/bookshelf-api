CREATE TABLE books (
  id SERIAL,
  ownerId integer NOT NULL,
  isbn VARCHAR(13) NOT NULL,
  title VARCHAR(255) NOT NULL,
  thumbnailUrl VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (ownerId) references users(id)
);