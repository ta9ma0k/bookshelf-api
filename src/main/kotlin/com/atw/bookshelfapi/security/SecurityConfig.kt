package com.atw.bookshelfapi.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

  @Bean
  fun securityFilterChain(
    http: HttpSecurity,
    authenticationManager: AuthenticationManager,
    objectMapper: ObjectMapper,
  ): SecurityFilterChain =
    http
      .cors { cors -> cors.configurationSource(corsConfigurationSource())}
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/login").permitAll()
          .anyRequest().authenticated()
      }
      .csrf { csrf -> csrf.disable() }
      .addFilter(AuthenticationFilter(authenticationManager))
      .addFilter(AuthorizationFilter(authenticationManager, objectMapper))
      .build()

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
    authenticationConfiguration.authenticationManager

  private fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration()
    configuration.allowedOrigins = listOf("http://localhost:5173")
    configuration.allowedHeaders = listOf(CorsConfiguration.ALL)
    configuration.allowedMethods = listOf("GET", "POST", "OPTIONS")
    configuration.exposedHeaders = listOf(HttpHeaders.AUTHORIZATION)

    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", configuration)
    return source
  }
}