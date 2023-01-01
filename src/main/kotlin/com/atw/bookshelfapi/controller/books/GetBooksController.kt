package com.atw.bookshelfapi.controller.books

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.usecase.query.books.GetPagingBooksQuery
import com.atw.bookshelfapi.usecase.query.common.PaginationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class BookResponseDto(
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
)

data class PagingBookResponseDto(
  val count: Long,
  val data: List<BookResponseDto>
)

@RestController
@RequestMapping("books")
class GetBooksController(
  private val getPagingBooksQuery: GetPagingBooksQuery
) : AuthController() {
  companion object {
    private const val DEFAULT_PAGE = 0
    private const val DEFAULT_LIMIT = 5
  }

  @GetMapping
  fun index(
    @RequestParam("page", required = false) page: Int?,
    @RequestParam("limit", required = false) limit: Int?
  ): ResponseEntity<PagingBookResponseDto> {
    val result = getPagingBooksQuery.exec(PaginationDto(page ?: DEFAULT_PAGE, limit ?: DEFAULT_LIMIT))
    return ResponseEntity.ok(
      PagingBookResponseDto(
        result.count,
        result.bookDtoList.map { BookResponseDto(it.isbn, it.title, it.thumbnailUrl) })
    )
  }
}