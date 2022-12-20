package com.atw.bookshelfapi.controller

import com.atw.bookshelfapi.infrastructure.postgres.doman.user.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userRepository: UserRepository
) {
  @GetMapping("/users")
  fun users(): List<String> {
    return userRepository.findAll().map { it.name.orEmpty() }
  }
}