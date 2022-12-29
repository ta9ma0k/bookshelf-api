package com.atw.bookshelfapi.domain.usageapplication

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId
import java.time.OffsetDateTime

data class UsageApplicationId(override val value: Long) : EntityId<Long>
class UsageApplication private constructor(
  id: UsageApplicationId?,
  val applicantId: UserId,
  val bookId: BookId,
  val status: UsageApplicationStatus,
  val requestedAt: OffsetDateTime,
  val picId: UserId? = null,
  val completedAt: OffsetDateTime? = null
) : EntityBase<UsageApplicationId>(id) {
  companion object {
    fun create(applicantId: UserId, bookId: BookId): UsageApplication =
      UsageApplication(null, applicantId, bookId, UsageApplicationStatus.PIC_NOT_ASSIGNED, OffsetDateTime.now())
  }
}