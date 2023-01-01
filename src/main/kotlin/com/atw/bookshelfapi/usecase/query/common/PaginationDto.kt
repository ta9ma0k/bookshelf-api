package com.atw.bookshelfapi.usecase.query.common

data class PaginationDto(
  val page: Int,
  val limit: Int
) {
  init {
    require(page >= 0) { "Page must bu positive value." }

    require(limit > 0) { "Paging limit must be integer." }
    require(limit <= MAX_SIZE) { "Paging limit must not be over ${MAX_SIZE}." }
  }

  companion object {
    private const val MAX_SIZE = 100
  }
}