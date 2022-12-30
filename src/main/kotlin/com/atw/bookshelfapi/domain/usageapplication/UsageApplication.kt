package com.atw.bookshelfapi.domain.usageapplication

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId
import java.time.OffsetDateTime

data class UsageApplicationId(override val value: Long) : EntityId<Long>
sealed class UsageApplication(
  id: UsageApplicationId?,
  val applicantId: UserId,
  val bookId: BookId,
  val requestedAt: OffsetDateTime
) : EntityBase<UsageApplicationId>(id) {
  companion object {
    fun create(applicantId: UserId, bookId: BookId): UsageApplication =
      PicNotAssigned.create(applicantId, bookId)
  }
}

class PicNotAssigned private constructor(
  id: UsageApplicationId?, applicationId: UserId, bookId: BookId, requestedAt: OffsetDateTime
) : UsageApplication(id, applicationId, bookId, requestedAt) {
  companion object {
    fun reconstruct(id: UsageApplicationId, applicantId: UserId, bookId: BookId, requestedAt: OffsetDateTime) =
      PicNotAssigned(id, applicantId, bookId, requestedAt)

    fun create(applicantId: UserId, bookId: BookId) = PicNotAssigned(null, applicantId, bookId, OffsetDateTime.now())
  }

  fun assign(picId: UserId) =
    PicAssigned.reconstruct(getId(), applicantId, bookId, requestedAt, picId)
}

class PicAssigned private constructor(
  id: UsageApplicationId, applicationId: UserId, bookId: BookId, requestedAt: OffsetDateTime, val picId: UserId
) : UsageApplication(id, applicationId, bookId, requestedAt) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      bookId: BookId,
      requestedAt: OffsetDateTime,
      picId: UserId
    ) =
      PicAssigned(id, applicantId, bookId, requestedAt, picId)
  }
}

class Received private constructor(
  id: UsageApplicationId,
  applicationId: UserId,
  bookId: BookId,
  requestedAt: OffsetDateTime,
  val picId: UserId,
  val completedAt: OffsetDateTime
) : UsageApplication(id, applicationId, bookId, requestedAt) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      bookId: BookId,
      requestedAt: OffsetDateTime,
      picId: UserId,
      completedAt: OffsetDateTime
    ) =
      Received(id, applicantId, bookId, requestedAt, picId, completedAt)
  }
}