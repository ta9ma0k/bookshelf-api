package com.atw.bookshelfapi.domain.assetmanagement

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.book.Isbn

interface AssetManagementRepository {
  fun save(aggregate: AssetManagement): BookId
  fun findByIsbn(isbn: Isbn): AssetManagement?
}