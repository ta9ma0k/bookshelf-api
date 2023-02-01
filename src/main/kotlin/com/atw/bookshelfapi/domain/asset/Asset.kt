package com.atw.bookshelfapi.domain.asset

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId

data class AssetId(override val value: Long) : EntityId<Long>
class Asset private constructor(
  id: AssetId?,
  val ownerId: UserId
) : EntityBase<AssetId>(id), Cloneable {
  companion object {
    fun reconstruct(id: AssetId, userId: UserId) = Asset(id, userId)
    fun create(userId: UserId) = Asset(null, userId)
  }

  fun changeOwner(newOwnerId: UserId) = reconstruct(getId(), newOwnerId)

  public override fun clone(): Asset = super.clone() as Asset
}