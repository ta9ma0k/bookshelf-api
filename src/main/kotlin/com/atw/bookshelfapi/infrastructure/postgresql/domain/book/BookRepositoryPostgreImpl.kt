package com.atw.bookshelfapi.infrastructure.postgresql.domain.book

import com.atw.bookshelfapi.domain.book.*
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookScheme
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryPostgreImpl(
  private val bookDao: BookDao
) : BookRepository {
  override fun save(entity: Book): BookId {
    val saveData = BookScheme(
      if (entity.hasId) entity.getId().value else null,
      entity.isbn.value,
      entity.title.value,
      entity.thumbnailUrl?.value
    )
    return BookId(bookDao.save(saveData).id!!)
  }

  override fun findByIsbn(isbn: Isbn): Book? =
    bookDao.findByIsbn(isbn.value)?.let {
      Book.reconstruct(
        BookId(it.id!!),
        Isbn(it.isbn!!),
        Title(it.title!!),
        it.thumbnailUrl?.let { v -> ThumbnailUrl(v) }
      )
    }
}