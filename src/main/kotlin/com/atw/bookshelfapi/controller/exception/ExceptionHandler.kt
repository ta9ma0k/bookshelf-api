package com.atw.bookshelfapi.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.Exception

@RestControllerAdvice
class ExceptionHandler {

  @ExceptionHandler(Exception::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleException(e: Exception): ErrorResponse {
    return ErrorResponse(e.message)
  }
}