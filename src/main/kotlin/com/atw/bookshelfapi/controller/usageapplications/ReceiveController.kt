package com.atw.bookshelfapi.controller.usageapplications

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.usecase.command.usageapplications.ReceiveUsageApplicationCommand
import com.atw.bookshelfapi.usecase.command.usageapplications.ReceiveUsageApplicationCommandDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("usage-applications/{applicationId}/receive")
class ReceiveController(
  private val receiveUsageApplicationCommand: ReceiveUsageApplicationCommand
) : AuthController() {
  @PostMapping
  fun index(@PathVariable("applicationId") applicationIdParam: Long): ResponseEntity<Long> =
    ResponseEntity.ok(
      receiveUsageApplicationCommand.exec(
        ReceiveUsageApplicationCommandDto(
          UsageApplicationId(applicationIdParam),
          getEmail()
        )
      ).value
    )
}