package com.atw.bookshelfapi.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import java.time.OffsetDateTime

class JWTUtils {
  companion object {
    private val algorithm = Algorithm.HMAC256("secret")
    private val verifier = JWT.require(algorithm).build()
    fun generateToken(username: String): String =
      JWT.create()
        .withSubject(username)
        .withIssuedAt(OffsetDateTime.now().toInstant())
        .withExpiresAt(OffsetDateTime.now().plusMinutes(30).toInstant())
        .sign(algorithm)

    fun validateToken(token: String): Boolean {
      try {
        verifier.verify(token)
      } catch (e: JWTVerificationException) {
        return false
      }
      return true
    }

    fun getSubject(token: String): String = verifier.verify(token).subject
  }
}