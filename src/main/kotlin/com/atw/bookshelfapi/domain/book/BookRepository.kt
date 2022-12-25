package com.atw.bookshelfapi.domain.book

interface BookRepository {
  fun save(entity: Book): BookId
  fun findByIsbn(isbn: Isbn): List<Book>
}