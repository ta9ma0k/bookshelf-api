package com.atw.bookshelfapi.domain.usageapplication

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId
import java.time.OffsetDateTime

data class UsageApplicationId(override val value: Long) : EntityId<Long>
sealed class UsageApplication(
  id: UsageApplicationId?,
  val applicantId: UserId,
  val requestedAt: OffsetDateTime,
  val reason: Reason
) : EntityBase<UsageApplicationId>(id) {
  companion object {
    fun create(applicantId: UserId, reason: Reason): UsageApplication =
      PicNotAssigned.create(applicantId, reason)
  }

  fun assign(picId: UserId): UsageApplication {
    if (this !is PicNotAssigned) {
      throw IllegalStateException()
    }
    return PicAssigned.reconstruct(getId(), applicantId, requestedAt, reason, picId)
  }

  fun received(): UsageApplication {
    if (this !is PicAssigned) {
      throw IllegalStateException()
    }
    return Received.reconstruct(getId(), applicantId, requestedAt, reason, picId, OffsetDateTime.now())
  }
}

class PicNotAssigned private constructor(
  id: UsageApplicationId?,
  applicationId: UserId,
  requestedAt: OffsetDateTime,
  reason: Reason
) : UsageApplication(id, applicationId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      requestedAt: OffsetDateTime,
      reason: Reason
    ) =
      PicNotAssigned(id, applicantId, requestedAt, reason)

    fun create(applicantId: UserId, reason: Reason) =
      PicNotAssigned(null, applicantId, OffsetDateTime.now(), reason)
  }
}

class PicAssigned private constructor(
  id: UsageApplicationId,
  applicationId: UserId,
  requestedAt: OffsetDateTime,
  reason: Reason,
  val picId: UserId
) : UsageApplication(id, applicationId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      requestedAt: OffsetDateTime,
      reason: Reason,
      picId: UserId
    ) =
      PicAssigned(id, applicantId, requestedAt, reason, picId)
  }
}

class Received private constructor(
  id: UsageApplicationId,
  applicationId: UserId,
  requestedAt: OffsetDateTime,
  reason: Reason,
  val picId: UserId,
  val completedAt: OffsetDateTime
) : UsageApplication(id, applicationId, requestedAt, reason) {
  companion object {
    fun reconstruct(
      id: UsageApplicationId,
      applicantId: UserId,
      requestedAt: OffsetDateTime,
      reason: Reason,
      picId: UserId,
      completedAt: OffsetDateTime
    ) =
      Received(id, applicantId, requestedAt, reason, picId, completedAt)
  }
}