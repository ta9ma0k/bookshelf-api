package com.atw.bookshelfapi.infrastructure.postgresql.dao.user

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "users")
data class UserScheme(
  @Id
  @GeneratedValue
  var id: Long?,
  @Column
  var name: String?,
  @Column(unique = true)
  var email: String?,
  var password: String?
)

interface UserDao: CrudRepository<UserScheme, Long> {
  fun findByEmail(email: String): UserScheme?
}