package com.atw.bookshelfapi.infrastructure.postgresql.dao.asset

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository


@Entity
@Table(name = "assets")
data class AssetScheme(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  @Column(name = "owner_id")
  val ownerId: Long,
  @Column(name = "book_id")
  val bookId: Long
)

interface AssetDao : CrudRepository<AssetScheme, Long> {
  fun findByBookId(bookId: Long): List<AssetScheme>
}