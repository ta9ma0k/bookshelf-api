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
  val requestedAt: OffsetDateTime,
  val reason: Reason
) : EntityBase<UsageApplicationId>(id) {
  companion object {
    fun create(applicantId: UserId, bookId: BookId, reason: Reason): UsageApplication =
      PicNotAssigned.create(applicantId, bookId, reason)
  }

  fun assign(picId: UserId): UsageApplication {
    if (this !is PicNotAssigned) {
      throw IllegalStateException()
    }
    return PicAssigned.reconstruct(getId(), applicantId, bookId, requestedAt, reason, picId)
  }

  fun received(): UsageApplication {
    if (this !is PicAssigned) {
      throw IllegalStateException()
    }
    return Received.reconstruct(getId(), applicantId, bookId, requestedAt, reason, picId, OffsetDateTime.now())
  }
}

class PicNotAssigned private constructor(
  id: UsageApplicationId?, applicationId: UserId, bookId: BookId, requestedAt: OffsetDateTime, reason: Reason
) : UsageApplication(id, applicationId, bookId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      bookId: BookId,
      requestedAt: OffsetDateTime,
      reason: Reason
    ) =
      PicNotAssigned(id, applicantId, bookId, requestedAt, reason)

    fun create(applicantId: UserId, bookId: BookId, reason: Reason) =
      PicNotAssigned(null, applicantId, bookId, OffsetDateTime.now(), reason)
  }
}

class PicAssigned private constructor(
  id: UsageApplicationId,
  applicationId: UserId,
  bookId: BookId,
  requestedAt: OffsetDateTime,
  reason: Reason,
  val picId: UserId
) : UsageApplication(id, applicationId, bookId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      bookId: BookId,
      requestedAt: OffsetDateTime,
      reason: Reason,
      picId: UserId
    ) =
      PicAssigned(id, applicantId, bookId, requestedAt, reason, picId)
  }
}

class Received private constructor(
  id: UsageApplicationId,
  applicationId: UserId,
  bookId: BookId,
  requestedAt: OffsetDateTime,
  reason: Reason,
  val picId: UserId,
  val completedAt: OffsetDateTime
) : UsageApplication(id, applicationId, bookId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      bookId: BookId,
      requestedAt: OffsetDateTime,
      reason: Reason,
      picId: UserId,
      completedAt: OffsetDateTime
    ) =
      Received(id, applicantId, bookId, requestedAt, reason, picId, completedAt)
  }
}