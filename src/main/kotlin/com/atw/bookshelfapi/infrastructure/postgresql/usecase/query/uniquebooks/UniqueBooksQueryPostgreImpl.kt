package com.atw.bookshelfapi.infrastructure.postgresql.usecase.query.uniquebooks

import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import com.atw.bookshelfapi.usecase.query.uniquebooks.BookDto
import com.atw.bookshelfapi.usecase.query.uniquebooks.UniqueBooksQuery
import org.springframework.stereotype.Service

@Service
class UniqueBooksQueryPostgreImpl(
    private val bookDao: BookDao
): UniqueBooksQuery {
  override fun getAll(): List<BookDto> {
    return bookDao.findAll()
        .groupBy { it.isbn }
        .values
        .map { BookDto(it.first().isbn!!, it.first().title!!, it.first().thumbnailUrl ) }
  }
}