package com.atw.bookshelfapi.domain.asset

interface AssetRepository {
  fun save(entity: Asset): AssetId
}