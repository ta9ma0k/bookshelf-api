package com.atw.bookshelfapi.controller.usageapplications

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.usecase.command.usageapplications.CreateUsageApplicationCommand
import com.atw.bookshelfapi.usecase.command.usageapplications.CreateUsageApplicationDto
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("usage-applications")
class CreateUsageApplicationController(
  private val createUsageApplicationCommand: CreateUsageApplicationCommand
) : AuthController() {
  @PostMapping
  fun index(@RequestBody request: Request): ResponseEntity<Long> =
    ResponseEntity.ok(
      createUsageApplicationCommand.exec(
        CreateUsageApplicationDto(getEmail(), Isbn(request.isbn))
      ).value
    )
}

data class Request @JsonCreator constructor(
  val isbn: String,
)