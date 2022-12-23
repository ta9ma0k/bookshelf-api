package com.atw.bookshelfapi.infrastructure.postgresql.usecase.query.security

import com.atw.bookshelfapi.infrastructure.postgresql.dao.user.UserDao
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsPostgreImpl(
  private val userDao: UserDao
) : UserDetailsService {
  override fun loadUserByUsername(username: String): UserDetails {
    val res = userDao.findByEmail(username) ?: throw UsernameNotFoundException(username)
    return User(res.email!!, res.password!!, emptyList())
  }
}