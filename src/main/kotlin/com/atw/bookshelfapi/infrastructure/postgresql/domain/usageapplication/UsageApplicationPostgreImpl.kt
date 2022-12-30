package com.atw.bookshelfapi.infrastructure.postgresql.domain.usageapplication

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.usageapplication.*
import com.atw.bookshelfapi.domain.user.UserId
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationDao
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationScheme
import com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication.UsageApplicationStatus
import org.springframework.stereotype.Repository

@Repository
class UsageApplicationPostgreImpl(
  private val usageApplicationDao: UsageApplicationDao
) : UsageApplicationRepository {
  override fun save(entity: UsageApplication): UsageApplicationId {
    val saveData = when (entity) {
      is PicNotAssigned -> UsageApplicationScheme(
        if (entity.hasId) entity.getId().value else null,
        entity.applicantId.value,
        entity.bookId.value,
        UsageApplicationStatus.PIC_NOT_ASSIGNED,
        entity.requestedAt,
        entity.reason.value,
        null, null
      )

      is PicAssigned -> UsageApplicationScheme(
        entity.getId().value,
        entity.applicantId.value,
        entity.bookId.value,
        UsageApplicationStatus.PIC_ASSIGNED,
        entity.requestedAt,
        entity.reason.value,
        entity.picId.value,
        null
      )

      is Received -> UsageApplicationScheme(
        entity.getId().value,
        entity.applicantId.value,
        entity.bookId.value,
        UsageApplicationStatus.RECEIVED,
        entity.requestedAt,
        entity.reason.value,
        entity.picId.value,
        entity.completedAt
      )
    }
    return UsageApplicationId(usageApplicationDao.save(saveData).id!!)
  }

  override fun findById(id: UsageApplicationId): UsageApplication? {
    return usageApplicationDao.findById(id.value).map {
      val usageApplicationId = UsageApplicationId(it.id!!)
      val applicantId = UserId(it.applicantId!!)
      val bookId = BookId(it.bookId!!)
      val requestedAt = it.requestedAt!!
      val reason = Reason(it.reason)
      val optionalPicId = it.picId?.let { v -> UserId(v) }
      when (it.status) {
        UsageApplicationStatus.PIC_NOT_ASSIGNED ->
          PicNotAssigned.reconstruct(
            usageApplicationId, applicantId, bookId, requestedAt, reason
          )

        UsageApplicationStatus.PIC_ASSIGNED -> PicAssigned.reconstruct(
          usageApplicationId, applicantId, bookId, requestedAt, reason, optionalPicId ?: throw IllegalStateException()
        )

        UsageApplicationStatus.RECEIVED -> Received.reconstruct(
          usageApplicationId,
          applicantId,
          bookId,
          requestedAt,
          reason,
          optionalPicId ?: throw IllegalStateException(),
          it.completedAt ?: throw IllegalStateException()
        )
      }
    }.orElse(null)
  }
}