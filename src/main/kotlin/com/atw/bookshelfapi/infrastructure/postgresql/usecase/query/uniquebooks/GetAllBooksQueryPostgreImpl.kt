package com.atw.bookshelfapi.infrastructure.postgresql.usecase.query.uniquebooks

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import com.atw.bookshelfapi.usecase.query.books.BookDto
import com.atw.bookshelfapi.usecase.query.books.GetAllBooksQuery
import org.springframework.stereotype.Service

@Service
class GetAllBooksQueryPostgreImpl(
  private val bookDao: BookDao
) : GetAllBooksQuery {
  override fun getAll(): List<BookDto> {
    return bookDao.findAll()
      .map { BookDto(Isbn(it.isbn), Title(it.title), it.thumbnailUrl?.let { v -> ThumbnailUrl(v) }) }
  }
}