package com.atw.bookshelfapi.controller.usageapplications

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.usecase.command.usageapplications.AssignUsageApplicationCommand
import com.atw.bookshelfapi.usecase.command.usageapplications.AssignUsageApplicationCommandDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("usage-applications/{applicationId}/assign")
class AssignPicController(
  private val assignUsageApplicationCommand: AssignUsageApplicationCommand
) : AuthController() {

  @PostMapping
  fun index(@PathVariable("applicationId") applicationIdParam: Long): ResponseEntity<Long> =
    ResponseEntity.ok(
      assignUsageApplicationCommand.exec(
        AssignUsageApplicationCommandDto(UsageApplicationId(applicationIdParam), getEmail())
      ).value
    )
}