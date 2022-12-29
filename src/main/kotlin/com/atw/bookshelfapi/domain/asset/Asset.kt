package com.atw.bookshelfapi.domain.asset

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId

data class AssetId(override val value: Long) : EntityId<Long>
class Asset private constructor(
  id: AssetId?,
  val bookId: BookId,
  val userId: UserId
) : EntityBase<AssetId>(id) {
  companion object {
    fun create(bookId: BookId, userId: UserId) = Asset(null, bookId, userId)
  }
}