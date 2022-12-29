package com.atw.bookshelfapi.infrastructure.postgresql.dao.book

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "books")
data class BookScheme(
  @Id
  @SequenceGenerator(name = "books_id_seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  val isbn: String?,
  val title: String?,
  @Column(name = "thumbnail_url")
  val thumbnailUrl: String?
)

interface BookDao : CrudRepository<BookScheme, Long>
