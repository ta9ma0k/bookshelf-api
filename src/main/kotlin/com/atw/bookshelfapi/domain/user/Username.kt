package com.atw.bookshelfapi.domain.user

@JvmInline
value class Username(val value: String) {
  init {
    require(value.isNotBlank()) { "Username must not be blank." }
  }
}