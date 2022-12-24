package com.atw.bookshelfapi.domain.book

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId


typealias BookId = EntityId<Long>

class Book(
  id: BookId?,
  val ownerId: UserId,
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
) : EntityBase<Long>(id)