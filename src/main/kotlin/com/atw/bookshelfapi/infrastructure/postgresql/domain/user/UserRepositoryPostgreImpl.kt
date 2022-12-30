package com.atw.bookshelfapi.infrastructure.postgresql.domain.user

import com.atw.bookshelfapi.domain.user.*
import com.atw.bookshelfapi.infrastructure.postgresql.dao.user.UserDao
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryPostgreImpl(
  private val userDao: UserDao
) : UserRepository {
  override fun findByEmail(email: Email): User? =
    userDao.findByEmail(email.value)?.let {
      User.reconstruct(UserId(it.id!!), Username(it.name), Email(it.email))
    }
}