package com.atw.bookshelfapi.usecase.query.uniquebooks

data class BookDto(
    val isbn: String,
    val title: String,
    val imgSrc: String?
)

interface UniqueBooksQuery {
  fun getAll(): List<BookDto>
}