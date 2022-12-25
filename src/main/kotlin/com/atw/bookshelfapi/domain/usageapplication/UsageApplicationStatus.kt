package com.atw.bookshelfapi.domain.usageapplication

enum class UsageApplicationStatus(val value: Int) {
  NOT_ASSIGNED(10),
  ASSIGNED(20),
  RECEIVED(30)
}