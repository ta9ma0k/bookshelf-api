package com.atw.bookshelfapi.controller.books

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.usecase.query.books.GetAllBooksQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class BookResponseDto(
  val isbn: String,
  val title: String,
  val imgSrc: String?
)

@RestController
class GetBooksController(
  private val getAllBooksQuery: GetAllBooksQuery
) : AuthController() {
  @GetMapping("books")
  fun index(): ResponseEntity<List<BookResponseDto>> =
    ResponseEntity.ok(
      getAllBooksQuery.getAll().map { BookResponseDto(it.isbn.value, it.title.value, it.thumbnailUrl?.value) })
}