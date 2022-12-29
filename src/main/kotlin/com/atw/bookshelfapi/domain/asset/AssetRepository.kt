package com.atw.bookshelfapi.domain.asset

import com.atw.bookshelfapi.domain.book.BookId

interface AssetRepository {
  fun save(entity: Asset): AssetId

  fun findByBook(bookId: BookId): List<Asset>
}