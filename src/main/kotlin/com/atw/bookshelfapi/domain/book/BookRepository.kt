package com.atw.bookshelfapi.domain.book

interface BookRepository {
  fun findByIsbn(isbn: Isbn): List<Book>
}