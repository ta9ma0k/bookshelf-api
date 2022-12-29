package com.atw.bookshelfapi.domain.book

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId

data class BookId(override val value: Long): EntityId<Long>

class Book private constructor(
  id: BookId?,
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?,
) : EntityBase<BookId>(id) {
  companion object {
    fun reconstruct(
      id: BookId,
      isbn: Isbn,
      title: Title,
      thumbnailUrl: ThumbnailUrl?,
    ) =
      Book(id, isbn, title, thumbnailUrl)

    fun create(isbn: Isbn, title: Title, thumbnailUrl: ThumbnailUrl?) =
      Book(null, isbn, title, thumbnailUrl)
  }
}