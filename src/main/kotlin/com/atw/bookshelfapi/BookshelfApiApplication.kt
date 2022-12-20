package com.atw.bookshelfapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookshelfApiApplication

fun main(args: Array<String>) {
	runApplication<BookshelfApiApplication>(*args)
}
