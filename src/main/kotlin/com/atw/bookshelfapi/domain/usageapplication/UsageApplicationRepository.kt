package com.atw.bookshelfapi.domain.usageapplication

interface UsageApplicationRepository {
  fun save(entity: UsageApplication): UsageApplicationId
}