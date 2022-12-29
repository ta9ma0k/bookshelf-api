package com.atw.bookshelfapi.usecase.query.books

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title

data class BookDto(
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
)

interface GetAllBooksQuery {
  fun getAll(): List<BookDto>
}