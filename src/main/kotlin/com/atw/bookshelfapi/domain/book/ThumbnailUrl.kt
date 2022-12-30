package com.atw.bookshelfapi.domain.book

@JvmInline
value class ThumbnailUrl(val value: String) {
  init {
    require(value.isNotBlank()) { "ThumbnailUrl must not be blank." }
  }
}