package com.atw.bookshelfapi.controller.registerbook

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.usecase.command.books.RegisterBookCommand
import com.atw.bookshelfapi.usecase.command.books.RegisterBookDto
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterBookController(
  private val registerBookCommand: RegisterBookCommand
) : AuthController() {
  @PostMapping("books")
  fun index(@RequestBody request: Request): ResponseEntity<Long> =
    ResponseEntity.ok(
      registerBookCommand.exec(
        RegisterBookDto(
          getEmail(),
          Isbn(request.isbn),
          Title(request.title),
          request.thumbnailUrl?.let { ThumbnailUrl(it) })
      ).value
    )
}

data class Request @JsonCreator constructor(
  val isbn: String,
  val title: String,
  val thumbnailUrl: String?
)