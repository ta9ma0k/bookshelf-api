package com.atw.bookshelfapi.controller.createusageapplication

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.usecase.command.usageapplication.CreateUsageApplicationCommand
import com.atw.bookshelfapi.usecase.command.usageapplication.CreateUsageApplicationDto
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateUsageApplicationController(
  private val createUsageApplicationCommand: CreateUsageApplicationCommand
) {
  @PostMapping("usage-applications")
  fun index(@RequestBody request: Request): ResponseEntity<Long> {
    val email = SecurityContextHolder.getContext().authentication.principal as Email
    return ResponseEntity.ok(
      createUsageApplicationCommand.create(
        CreateUsageApplicationDto(
          email,
          Isbn(request.isbn)
        )
      ).value
    )
  }
}

data class Request @JsonCreator constructor(
  val isbn: String
)