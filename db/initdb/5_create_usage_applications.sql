create table usage_applications (
  id SERIAL,
  applicant_id integer not null,
  isbn varchar(13) not null,
  requested_at timestamp not null,
  status integer not null,
  book_id integer,
  completed_at timestamp,
  primary key (id),
  foreign key (applicant_id) references users(id),
  foreign key (book_id) references books(id)
);