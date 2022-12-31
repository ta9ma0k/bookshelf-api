package com.atw.bookshelfapi.infrastructure.postgresql.domain.book

import com.atw.bookshelfapi.domain.book.BookAggregate
import com.atw.bookshelfapi.domain.book.BookAggregateRepository
import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.infrastructure.postgresql.dao.asset.AssetDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.asset.AssetScheme
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationScheme
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class BookAggregateRepositoryPostgreImpl(
  private val bookDao: BookDao,
  private val assetDao: AssetDao,
  private val usageApplicationDao: UsageApplicationDao
) : BookAggregateRepository {
  override fun save(aggregate: BookAggregate): BookId {
    assetDao.saveAll(aggregate.assets.map { AssetScheme.fromAssetEntity(it, aggregate.bookId) })
    usageApplicationDao.saveAll(aggregate.usageApplications.map {
      UsageApplicationScheme.fromUsageApplicationEntity(
        it,
        aggregate.bookId
      )
    })
    return aggregate.bookId
  }

  override fun findByIsbn(isbn: Isbn): BookAggregate? {
    val book = bookDao.findByIsbn(isbn.value) ?: return null
    if (book.id == null) {
      return null
    }
    val assets = assetDao.findByBookId(book.id)
    val usageApplications = usageApplicationDao.findByBookId(book.id)
    return BookAggregate.reconstruct(
      BookId(book.id),
      assets.map { it.toAssetEntity() },
      usageApplications.map { it.toUsageApplicationEntity() }
    )
  }
}