package com.atw.bookshelfapi.controller.uniquebooks

import com.atw.bookshelfapi.usecase.query.uniquebooks.UniqueBooksQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class BookResponseDto(
    val isbn: String,
    val title: String,
    val imgSrc: String?
)

@RestController
class UniqueBooksController(
    private val uniqueBooksQuery: UniqueBooksQuery
) {
  @GetMapping("/unique-books")
  fun index(): List<BookResponseDto> {
    return uniqueBooksQuery.getAll().map { BookResponseDto(it.isbn, it.title, it.imgSrc) }
  }
}