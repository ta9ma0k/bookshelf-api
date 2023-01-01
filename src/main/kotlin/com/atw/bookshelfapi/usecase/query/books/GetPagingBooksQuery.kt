package com.atw.bookshelfapi.usecase.query.books

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.usecase.query.common.PaginationDto

data class BookDto(
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
)

data class PagingBookDto(
  val count: Long,
  val bookDtoList: List<BookDto>
)

interface GetPagingBooksQuery {
  fun exec(paginationDto: PaginationDto): PagingBookDto
}