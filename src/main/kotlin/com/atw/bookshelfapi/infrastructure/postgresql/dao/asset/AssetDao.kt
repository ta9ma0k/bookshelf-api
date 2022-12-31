package com.atw.bookshelfapi.infrastructure.postgresql.dao.asset

import com.atw.bookshelfapi.domain.asset.Asset
import com.atw.bookshelfapi.domain.asset.AssetId
import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.user.UserId
import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository


@Entity
@Table(name = "assets")
data class AssetScheme(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  @Column(name = "book_id")
  val bookId: Long,
  @Column(name = "owner_id")
  val ownerId: Long
) {
  companion object {
    fun fromAssetEntity(entity: Asset, bookId: BookId): AssetScheme =
      AssetScheme(
        if (entity.hasId) entity.getId().value else null,
        bookId.value,
        entity.ownerId.value
      )
  }

  fun toAssetEntity(): Asset = Asset.reconstruct(
    id?.let { AssetId(it) } ?: throw IllegalStateException("Asset doe's not have a id."),
    UserId(ownerId)
  )
}

interface AssetDao : CrudRepository<AssetScheme, Long> {
  fun findByBookId(bookId: Long): List<AssetScheme>
}