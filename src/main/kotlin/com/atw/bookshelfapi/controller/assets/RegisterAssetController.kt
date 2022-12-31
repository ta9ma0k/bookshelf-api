package com.atw.bookshelfapi.controller.assets

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.usecase.command.assets.RegisterAssetCommand
import com.atw.bookshelfapi.usecase.command.assets.RegisterAssetDto
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books/{isbn}/assets")
class RegisterAssetController(
  private val registerAssetCommand: RegisterAssetCommand
) : AuthController() {

  @PostMapping
  fun index(
    @PathVariable("isbn") isbnParam: String,
    @RequestBody request: Request
  ): ResponseEntity<Any> {
    val dto = RegisterAssetDto(
      Isbn(isbnParam),
      Title(request.title),
      request.thumbnailUrl?.let { ThumbnailUrl(request.thumbnailUrl) },
      getEmail()
    )
    registerAssetCommand.exec(dto)
    return ResponseEntity.ok().build()
  }
}

data class Request @JsonCreator constructor(
  val title: String,
  val thumbnailUrl: String?
)