package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.user.Email

data class AssignUsageApplicationCommandDto(
  val isbn: Isbn,
  val usageApplicationId: UsageApplicationId,
  val picEmail: Email
)

interface AssignUsageApplicationCommand {
  fun exec(dto: AssignUsageApplicationCommandDto)
}