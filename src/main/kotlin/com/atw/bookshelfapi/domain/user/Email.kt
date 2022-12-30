package com.atw.bookshelfapi.domain.user

@JvmInline
value class Email(val value: String) {
  init {
    require(value.isNotBlank()) { "Email must not be blank." }
    //TODO メールアドレスのバリデーションをかける
  }
}
