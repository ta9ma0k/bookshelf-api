package com.atw.bookshelfapi.infrastructure.postgresql.usecase.query.usageapplications

import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationStatus
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.domain.user.Username
import com.atw.bookshelfapi.usecase.query.usageapplications.GetAllUsageApplicationsQuery
import com.atw.bookshelfapi.usecase.query.usageapplications.UsageApplicationDto
import jakarta.persistence.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class GetAllUsageApplicationsQueryPostgreImpl(
  @PersistenceContext
  private val em: EntityManager
) : GetAllUsageApplicationsQuery {

  companion object {
    private const val usageApplicationQuery = """
      select 
        ua.id, 
        applicant.email as applicant_email,
        applicant.name as applicant_name,
        books.id as book_id,
        books.title,
        ua.status,
        ua.requested_at,
        pic.name as pic_name,
        ua.completed_at
      from usage_applications as ua
      inner join users as applicant on applicant.id = ua.applicant_id
      inner join books on books.id = ua.book_id
      left outer join users as pic on pic.id = ua.pic_id
    """

    private const val assetsQuery = """
      select
        assets.book_id
      from assets
      inner join users on users.id = assets.owner_id
      where users.email = :email
    """
  }

  override fun exec(inquiryUserEmail: Email): List<UsageApplicationDto> {
    val usageApplicationsQueryResults =
      em.createNativeQuery(usageApplicationQuery, QueryResult::class.java).resultList.toList() as List<QueryResult>
    val assetsQueryResults = em.createNativeQuery(assetsQuery, Long::class.java)
      .setParameter("email", inquiryUserEmail.value).resultList.toList() as List<Long>
    return usageApplicationsQueryResults.map { it.toDto(inquiryUserEmail, assetsQueryResults) }
  }
}

@Entity
data class QueryResult(
  @Id
  val id: Long,
  @Column(name = "applicant_email")
  val applicantEmail: String,
  @Column(name = "applicant_name")
  val applicantName: String,
  @Column(name = "book_id")
  val bookId: Long,
  val title: String,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  val status: UsageApplicationStatus,
  @Column(name = "requested_at")
  val requestedAt: OffsetDateTime,
  @Column(name = "pic_name")
  val picName: String?,
  @Column(name = "completed_at")
  val completedAt: OffsetDateTime?
) {
  fun toDto(inquiryUserEmail: Email, assetBookIds: List<Long>): UsageApplicationDto {

    return when (status) {
      UsageApplicationStatus.PIC_NOT_ASSIGNED -> {
        UsageApplicationDto.PicNotAssigned(
          UsageApplicationId(id), Title(title), Username(applicantName), requestedAt, assetBookIds.contains(bookId)
        )
      }

      UsageApplicationStatus.PIC_ASSIGNED -> {
        val picUsername = picName?.let { Username(it) } ?: throw IllegalStateException()
        UsageApplicationDto.PicAssigned(
          UsageApplicationId(id),
          Title(title),
          Username(applicantName),
          requestedAt,
          picUsername,
          Email(applicantEmail) == inquiryUserEmail
        )
      }

      UsageApplicationStatus.RECEIVED -> {
        val picUsername = picName?.let { Username(it) } ?: throw IllegalStateException()
        if (completedAt == null) {
          throw IllegalStateException()
        }
        UsageApplicationDto.Received(
          UsageApplicationId(id),
          Title(title),
          Username(applicantName),
          requestedAt,
          picUsername,
          completedAt
        )
      }
    }
  }
}

