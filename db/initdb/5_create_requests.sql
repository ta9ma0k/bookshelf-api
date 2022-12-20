create table requests (
  id SERIAL,
  applicantId integer not null,
  isbn varchar(13) not null,
  createdAt timestamp not null,
  status integer not null,
  bookId integer,
  completedAt timestamp,
  primary key (id),
  foreign key (applicantId) references users(id),
  foreign key (bookId) references books(id)
);