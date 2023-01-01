package com.atw.bookshelfapi.infrastructure.postgresql.usecase.query.books

import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.infrastructure.postgresql.dao.book.BookDao
import com.atw.bookshelfapi.usecase.query.books.BookDto
import com.atw.bookshelfapi.usecase.query.books.GetPagingBooksQuery
import com.atw.bookshelfapi.usecase.query.books.PagingBookDto
import com.atw.bookshelfapi.usecase.query.common.PaginationDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class GetPagingBooksQueryPostgreImpl(
  private val bookDao: BookDao
) : GetPagingBooksQuery {

  override fun exec(paginationDto: PaginationDto): PagingBookDto {
    val pageable = PageRequest.of(paginationDto.page, paginationDto.limit)
    val results = bookDao.findAll(pageable)
    return PagingBookDto(
      results.totalElements,
      results.map { BookDto(Isbn(it.isbn), Title(it.title), it.thumbnailUrl?.let { v -> ThumbnailUrl(v) }) }.toList()
    )
  }
}