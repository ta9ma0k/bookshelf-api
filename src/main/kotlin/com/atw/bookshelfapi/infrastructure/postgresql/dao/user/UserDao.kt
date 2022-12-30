package com.atw.bookshelfapi.infrastructure.postgresql.dao.user

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "users")
data class UserScheme(
  @Id
  val id: Long?,
  @Column
  val name: String,
  @Column(unique = true)
  val email: String,
  val password: String
)

interface UserDao : CrudRepository<UserScheme, Long> {
  fun findByEmail(email: String): UserScheme?
}