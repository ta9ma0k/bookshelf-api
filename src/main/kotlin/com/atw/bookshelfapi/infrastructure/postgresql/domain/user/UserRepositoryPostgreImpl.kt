package com.atw.bookshelfapi.infrastructure.postgresql.domain.user

import com.atw.bookshelfapi.domain.user.*
import com.atw.bookshelfapi.infrastructure.postgresql.dao.user.UserDao
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryPostgreImpl(
  private val userDao: UserDao
) : UserRepository {
  override fun findByEmail(email: Email): User? {
    val res = userDao.findByEmail(email.value) ?: return null
    if (res.name == null || res.email == null) {
      return null
    }
    return User.reconstruct(UserId(res.id!!), Username(res.name), Email(res.email))
  }
}