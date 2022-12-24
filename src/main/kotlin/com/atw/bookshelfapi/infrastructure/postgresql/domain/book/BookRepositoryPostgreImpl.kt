package com.atw.bookshelfapi.infrastructure.postgresql.domain.book

import com.atw.bookshelfapi.domain.book.*
import com.atw.bookshelfapi.domain.user.UserId
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryPostgreImpl(
  private val bookDao: BookDao
) : BookRepository {
  override fun findByIsbn(isbn: Isbn): List<Book> {
    return bookDao.findByIsbn(isbn.value)
      .map {
        Book(
          BookId(it.id!!),
          UserId(it.ownerId!!),
          Isbn(it.isbn!!),
          Title(it.title!!),
          it.thumbnailUrl?.let { url -> ThumbnailUrl(url) })
      }
  }
}