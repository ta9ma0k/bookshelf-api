package com.atw.bookshelfapi.infrastructure.postgresql.dao.book

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

@Entity
@Table(name = "books")
data class BookScheme(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  val isbn: String,
  val title: String,
  @Column(name = "thumbnail_url")
  val thumbnailUrl: String?
)

interface BookDao : PagingAndSortingRepository<BookScheme, Long>, CrudRepository<BookScheme, Long> {
  fun findByIsbn(isbn: String): BookScheme?
}
