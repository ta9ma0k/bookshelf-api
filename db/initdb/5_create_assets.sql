create table assets (
  id integer generated always as identity,
  owner_id integer not null,
  book_id integer not null,
  primary key (id),
  foreign key (owner_id) references users(id),
  foreign key (book_id) references books(id)
);