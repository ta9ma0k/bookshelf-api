package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.user.Email

data class ReceiveUsageApplicationCommandDto(
  val usageApplicationId: UsageApplicationId,
  val applicantEmail: Email
)

interface ReceiveUsageApplicationCommand {
  fun exec(dto: ReceiveUsageApplicationCommandDto): UsageApplicationId
}