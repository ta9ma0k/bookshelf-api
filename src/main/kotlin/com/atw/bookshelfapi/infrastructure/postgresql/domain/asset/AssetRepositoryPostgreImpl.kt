package com.atw.bookshelfapi.infrastructure.postgresql.domain.asset

import com.atw.bookshelfapi.domain.asset.Asset
import com.atw.bookshelfapi.domain.asset.AssetId
import com.atw.bookshelfapi.domain.asset.AssetRepository
import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.user.UserId
import com.atw.bookshelfapi.infrastructure.postgresql.dao.asset.AssetDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.asset.AssetScheme
import org.springframework.stereotype.Repository

@Repository
class AssetRepositoryPostgreImpl(
  private val assetDao: AssetDao
) : AssetRepository {
  override fun save(entity: Asset): AssetId {
    val saveData = AssetScheme(
      if (entity.hasId) entity.getId().value else null,
      entity.userId.value,
      entity.bookId.value
    )
    return AssetId(assetDao.save(saveData).id!!)
  }

  override fun findByBook(bookId: BookId): List<Asset> =
    assetDao.findByBookId(bookId.value).map {
      Asset.reconstruct(
        AssetId(it.id!!),
        BookId(it.bookId),
        UserId(it.ownerId)
      )
    }
}