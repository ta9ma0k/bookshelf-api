package com.atw.bookshelfapi.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthenticationFilter(
  authenticationManager: AuthenticationManager,
  private val userDetailsService: UserDetailsService
) : BasicAuthenticationFilter(authenticationManager) {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response)
      return
    }

    val token = authorizationHeader.substring(7)
    if (!JWTUtils.validateToken(token)) {
      chain.doFilter(request, response)
      return
    }

    val username = JWTUtils.getSubject(token)
    val userDetails = userDetailsService.loadUserByUsername(username)

    val authentication =
      UsernamePasswordAuthenticationToken(userDetails, null, emptyList())
    SecurityContextHolder.getContext().authentication = authentication

    chain.doFilter(request, response)
  }
}