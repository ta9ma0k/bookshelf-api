package com.atw.bookshelfapi.domain.book

interface BookAggregateRepository {
  fun save(aggregate: BookAggregate): BookId
  fun findByIsbn(isbn: Isbn): BookAggregate?
}