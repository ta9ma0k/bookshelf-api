package com.atw.bookshelfapi.controller.usageapplications

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.usageapplication.Reason
import com.atw.bookshelfapi.usecase.command.usageapplications.CreateUsageApplicationCommand
import com.atw.bookshelfapi.usecase.command.usageapplications.CreateUsageApplicationDto
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books/{isbn}/usage-applications")
class CreateUsageApplicationController(
  private val createUsageApplicationCommand: CreateUsageApplicationCommand
) : AuthController() {
  @PostMapping
  fun index(
    @PathVariable("isbn") isbnPram: String,
    @RequestBody request: Request
  ): ResponseEntity<Any> {
    val dto = CreateUsageApplicationDto(getEmail(), Isbn(isbnPram), Reason(request.reason))
    createUsageApplicationCommand.exec(dto)
    return ResponseEntity.ok().build()
  }
}

data class Request @JsonCreator constructor(
  val reason: String
)