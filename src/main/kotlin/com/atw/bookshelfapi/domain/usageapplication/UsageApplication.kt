package com.atw.bookshelfapi.domain.usageapplication

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId
import com.atw.bookshelfapi.domain.user.UserId
import java.time.OffsetDateTime

typealias UsageApplicationId = EntityId<Long>

class UsageApplication private constructor(
  id: UsageApplicationId?,
  val applicantId: UserId,
  val isbn: Isbn,
  val requestedAt: OffsetDateTime,
  val status: UsageApplicationStatus,
  val bookId: BookId?,
  val completedAt: OffsetDateTime?
) : EntityBase<Long>(id) {
  init {
    if (status == UsageApplicationStatus.RECEIVED) {
      require(bookId != null) { "BookId must not be null if status is complete." }
      require(completedAt != null) { "CompletedAt must not be null if status is complete." }
    }
  }

  companion object {
    fun create(applicantId: UserId, isbn: Isbn) =
      UsageApplication(null, applicantId, isbn, OffsetDateTime.now(), UsageApplicationStatus.NOT_ASSIGNED, null, null)
  }
}