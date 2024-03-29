create table usage_applications (
  id integer generated always as identity,
  applicant_id integer not null,
  book_id integer not null,
  status VARCHAR not null,
  requested_at timestamp not null,
  reason text not null,
  pic_id integer,
  completed_at timestamp,
  primary key (id),
  foreign key (applicant_id) references users(id),
  foreign key (book_id) references books(id),
  foreign key (pic_id) references users(id)
);