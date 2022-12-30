package com.atw.bookshelfapi.domain.usageapplication

@JvmInline
value class Reason(
  val value: String
) {
  init {
    require(value.isNotBlank()) { "Reason must be not blank." }
  }
}