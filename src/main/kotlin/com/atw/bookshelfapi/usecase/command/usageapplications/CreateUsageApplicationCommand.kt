package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.user.Email

data class CreateUsageApplicationDto(
  val applicantEmail: Email,
  val isbn: Isbn
)

interface CreateUsageApplicationCommand {
  fun exec(dto: CreateUsageApplicationDto): UsageApplicationId
}