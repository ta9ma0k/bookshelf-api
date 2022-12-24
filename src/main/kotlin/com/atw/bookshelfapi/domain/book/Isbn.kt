package com.atw.bookshelfapi.domain.book

@JvmInline
value class Isbn(
  val value: String
) {
  init {
    require(pattern.matches(value)) {
      "$value does not match the pattern $pattern."
    }
  }

  companion object {
    val pattern = "[0-9]{10}|[0-9]{13}".toRegex()
  }
}