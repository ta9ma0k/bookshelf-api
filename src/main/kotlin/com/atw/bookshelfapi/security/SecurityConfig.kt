package com.atw.bookshelfapi.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

  @Bean
  fun securityFilterChain(
    http: HttpSecurity,
    authenticationManager: AuthenticationManager,
    userDetailsService: UserDetailsService,
    objectMapper: ObjectMapper,
  ): SecurityFilterChain =
    http
      .cors {}
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/login").permitAll()
          .anyRequest().authenticated()
      }
      .csrf { csrf -> csrf.disable() }
      .addFilter(AuthenticationFilter(authenticationManager, userDetailsService))
      .addFilter(AuthorizationFilter(authenticationManager, objectMapper))
      .build()

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
    authenticationConfiguration.authenticationManager
}