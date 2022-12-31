package com.atw.bookshelfapi.infrastructure.postgresql.dao.usageapplication

import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.usageapplication.*
import com.atw.bookshelfapi.domain.user.UserId
import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import java.time.OffsetDateTime

@Entity
@Table(name = "usage_applications")
data class UsageApplicationScheme(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  @Column(name = "applicant_id")
  val applicantId: Long,
  @Column(name = "book_id")
  val bookId: Long,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  val status: UsageApplicationStatus,
  @Column(name = "requested_at")
  val requestedAt: OffsetDateTime,
  val reason: String,
  @Column(name = "pic_id")
  val picId: Long?,
  @Column(name = "completed_at")
  val completedAt: OffsetDateTime?
) {
  companion object {
    fun fromUsageApplicationEntity(entity: UsageApplication, bookId: BookId): UsageApplicationScheme =
      when (entity) {
        is PicNotAssigned -> UsageApplicationScheme(
          if (entity.hasId) entity.getId().value else null,
          entity.applicantId.value,
          bookId.value,
          UsageApplicationStatus.PIC_NOT_ASSIGNED,
          entity.requestedAt,
          entity.reason.value,
          null, null
        )

        is PicAssigned -> UsageApplicationScheme(
          entity.getId().value,
          entity.applicantId.value,
          bookId.value,
          UsageApplicationStatus.PIC_ASSIGNED,
          entity.requestedAt,
          entity.reason.value,
          entity.picId.value,
          null
        )

        is Received -> UsageApplicationScheme(
          entity.getId().value,
          entity.applicantId.value,
          bookId.value,
          UsageApplicationStatus.RECEIVED,
          entity.requestedAt,
          entity.reason.value,
          entity.picId.value,
          entity.completedAt
        )
      }
  }

  fun toUsageApplicationEntity(): UsageApplication {
    val usageApplicationId =
      id?.let { UsageApplicationId(it) } ?: throw IllegalStateException("UsageApplication doe's not have a Id.")
    val applicantId = UserId(applicantId)
    val requestedAt = requestedAt
    val reason = Reason(reason)
    val optionalPicId = picId?.let { v -> UserId(v) }
    return when (status) {
      UsageApplicationStatus.PIC_NOT_ASSIGNED ->
        PicNotAssigned.reconstruct(
          usageApplicationId, applicantId, requestedAt, reason
        )

      UsageApplicationStatus.PIC_ASSIGNED -> PicAssigned.reconstruct(
        usageApplicationId,
        applicantId,
        requestedAt,
        reason,
        optionalPicId ?: throw IllegalStateException("UsageApplication must have a pic id. [ID=$id]")
      )

      UsageApplicationStatus.RECEIVED -> Received.reconstruct(
        usageApplicationId,
        applicantId,
        requestedAt,
        reason,
        optionalPicId ?: throw IllegalStateException("UsageApplication must have a picId. [ID=$id]"),
        completedAt ?: throw IllegalStateException("UsageApplication must have a completedAt. [ID=$id]")
      )
    }
  }
}

interface UsageApplicationDao : CrudRepository<UsageApplicationScheme, Long> {
  fun findByBookId(bookId: Long): List<UsageApplicationScheme>
}