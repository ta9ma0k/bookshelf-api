package com.atw.bookshelfapi.infrastructure.postgresql.domain.usageapplication

import com.atw.bookshelfapi.domain.usageapplication.UsageApplication
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationRepository
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationScheme
import org.springframework.stereotype.Repository

@Repository
class UsageApplicationRepositoryPostgreImpl(
  private val usageApplicationDao: UsageApplicationDao
) : UsageApplicationRepository {
  override fun save(entity: UsageApplication): UsageApplicationId {
    val saveData = UsageApplicationScheme(
      if (entity.hasId) entity.getId().value else null,
      entity.applicantId.value,
      entity.isbn.value,
      entity.requestedAt,
      entity.status.value,
      entity.bookId?.value,
      entity.completedAt
    )
    return UsageApplicationId(usageApplicationDao.save(saveData).id!!)
  }
}