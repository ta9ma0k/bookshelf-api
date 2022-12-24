package com.atw.bookshelfapi.domain.user

interface UserRepository {
  fun findByEmail(email: Email): User?
}