package com.atw.bookshelfapi.usecase.query.usageapplications

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.domain.usageapplication.Reason
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.domain.user.Username
import java.time.OffsetDateTime

interface GetAllUsageApplicationsQuery {
  fun exec(inquiryUserEmail: Email): List<UsageApplicationDto>
}

sealed interface UsageApplicationDto {
  val id: UsageApplicationId
  val isbn: Isbn
  val bookTitle: Title
  val applicant: Username
  val requestedAt: OffsetDateTime
  val reason: Reason

  data class PicNotAssigned(
    override val id: UsageApplicationId,
    override val isbn: Isbn,
    override val bookTitle: Title,
    override val applicant: Username,
    override val requestedAt: OffsetDateTime,
    override val reason: Reason,
    val canAssign: Boolean
  ) : UsageApplicationDto

  data class PicAssigned(
    override val id: UsageApplicationId,
    override val isbn: Isbn,
    override val bookTitle: Title,
    override val applicant: Username,
    override val requestedAt: OffsetDateTime,
    override val reason: Reason,
    val pic: Username,
    val canReceive: Boolean
  ) : UsageApplicationDto

  data class Received(
    override val id: UsageApplicationId,
    override val isbn: Isbn,
    override val bookTitle: Title,
    override val applicant: Username,
    override val requestedAt: OffsetDateTime,
    override val reason: Reason,
    val pic: Username,
    val completedAt: OffsetDateTime
  ) : UsageApplicationDto
}