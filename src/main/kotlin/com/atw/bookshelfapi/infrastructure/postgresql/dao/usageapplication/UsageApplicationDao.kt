package com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import java.time.OffsetDateTime

@Entity
@Table(name = "usage_applications")
data class UsageApplicationScheme(
  @Id
  @SequenceGenerator(name = "usage_applications_id_seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  @Column(name = "applicant_id")
  val applicantId: Long?,
  val isbn: String?,
  @Column(name = "requested_at")
  val requestedAt: OffsetDateTime?,
  val status: Int?,
  @Column(name = "book_id")
  val bookId: Long?,
  @Column(name = "completed_at")
  val completedAt: OffsetDateTime?
)

interface UsageApplicationDao : CrudRepository<UsageApplicationScheme, Long>