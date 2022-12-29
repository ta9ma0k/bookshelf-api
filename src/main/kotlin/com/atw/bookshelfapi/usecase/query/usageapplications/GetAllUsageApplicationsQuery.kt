package com.atw.bookshelfapi.usecase.query.usageapplications

import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.domain.user.Username
import java.time.OffsetDateTime

interface GetAllUsageApplicationsQuery {
  fun exec(inquiryUserEmail: Email): List<UsageApplicationDto>
}

sealed class UsageApplicationDto {
  data class PicNotAssigned(
    val id: UsageApplicationId,
    val bookTitle: Title,
    val applicant: Username,
    val requestedAt: OffsetDateTime,
    val canAssign: Boolean
  ) : UsageApplicationDto()

  data class PicAssigned(
    val id: UsageApplicationId,
    val bookTitle: Title,
    val applicant: Username,
    val requestedAt: OffsetDateTime,
    val pic: Username,
    val canReceive: Boolean
  ): UsageApplicationDto()

  data class Received(
    val id: UsageApplicationId,
    val bookTitle: Title,
    val applicant: Username,
    val requestedAt: OffsetDateTime,
    val pic: Username,
    val completedAt: OffsetDateTime
  ): UsageApplicationDto()
}