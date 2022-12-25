package com.atw.bookshelfapi.domain.book

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId


typealias BookId = EntityId<Long>

class Book private constructor(
  id: BookId?,
  val ownerId: UserId,
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
) : EntityBase<Long>(id) {
  companion object {
    fun of(id: BookId, ownerId: UserId, isbn: Isbn, title: Title, thumbnailUrl: ThumbnailUrl?) =
      Book(id, ownerId, isbn, title, thumbnailUrl)

    fun create(ownerId: UserId, isbn: Isbn, title: Title, thumbnailUrl: ThumbnailUrl?) =
      Book(null, ownerId, isbn, title, thumbnailUrl)
  }
}