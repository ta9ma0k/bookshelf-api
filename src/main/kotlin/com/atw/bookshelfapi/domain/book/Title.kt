package com.atw.bookshelfapi.domain.book

@JvmInline
value class Title(val value: String) {
  init {
    require(value.isNotBlank()) { "Title must not be blank." }
  }
}