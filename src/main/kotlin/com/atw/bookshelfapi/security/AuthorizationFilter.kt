package com.atw.bookshelfapi.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthorizationFilter(
  authenticationManager: AuthenticationManager,
  private val objectMapper: ObjectMapper,
) : UsernamePasswordAuthenticationFilter() {
  init {
    this.authenticationManager = authenticationManager
  }

  override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
    val authRequest = this.objectMapper.readValue(request.inputStream, AuthRequest::class.java)
    return this.authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(
        authRequest.email,
        authRequest.password
      )
    )
  }

  override fun successfulAuthentication(
    request: HttpServletRequest,
    response: HttpServletResponse,
    chain: FilterChain,
    authResult: Authentication
  ) {
    val user = authResult.principal as UserDetails
    val token = JWTUtils.generateToken(user.username)

    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
  }
}

data class AuthRequest(
  val email: String,
  val password: String
)