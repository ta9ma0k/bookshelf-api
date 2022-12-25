package com.atw.bookshelfapi.infrastructure.postgresql.domain.book

import com.atw.bookshelfapi.domain.book.*
import com.atw.bookshelfapi.domain.user.UserId
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
      entity.ownerId.value,
      entity.isbn.value,
      entity.title.value,
      entity.thumbnailUrl?.value
    )
    return BookId(bookDao.save(saveData).id!!)
  }

  override fun findByIsbn(isbn: Isbn): List<Book> {
    return bookDao.findByIsbn(isbn.value)
      .map {
        Book.of(
          BookId(it.id!!),
          UserId(it.ownerId!!),
          Isbn(it.isbn!!),
          Title(it.title!!),
          it.thumbnailUrl?.let { url -> ThumbnailUrl(url) })
      }
  }
}