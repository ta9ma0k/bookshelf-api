package com.atw.bookshelfapi.controller.common

import com.atw.bookshelfapi.domain.user.Email
import org.springframework.security.core.context.SecurityContextHolder

open class AuthController {
  fun getEmail(): Email =
    SecurityContextHolder.getContext().authentication.principal as Email
}