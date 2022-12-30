package com.atw.bookshelfapi.controller.usageapplications

import com.atw.bookshelfapi.controller.common.AuthController
import com.atw.bookshelfapi.usecase.query.usageapplications.GetAllUsageApplicationsQuery
import com.atw.bookshelfapi.usecase.query.usageapplications.UsageApplicationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("usage-applications")
class GetUsageApplicationsController(
  private val getAllUsageApplicationsQuery: GetAllUsageApplicationsQuery
) : AuthController() {
  @GetMapping
  fun index(): ResponseEntity<List<ResponseDto>> =
    ResponseEntity.ok(getAllUsageApplicationsQuery.exec(getEmail()).map(ResponseDto::fromDto))
}

data class ResponseDto(
  val id: Long,
  val bookTitle: String,
  val applicant: String,
  val status: String,
  val requestDateTime: OffsetDateTime,
  val canUpdateStatus: Boolean? = null,
  val pic: String? = null,
  val receivedDateTime: OffsetDateTime? = null
) {
  companion object {
    fun fromDto(dto: UsageApplicationDto) =
      when (dto) {
        is UsageApplicationDto.PicNotAssigned -> ResponseDto(
          dto.id.value,
          dto.bookTitle.value,
          dto.applicant.value,
          "not_assigned",
          dto.requestedAt,
          dto.canAssign
        )

        is UsageApplicationDto.PicAssigned -> ResponseDto(
          dto.id.value,
          dto.bookTitle.value,
          dto.applicant.value,
          "assigned",
          dto.requestedAt,
          dto.canReceive,
          dto.pic.value
        )

        is UsageApplicationDto.Received -> ResponseDto(
          dto.id.value,
          dto.bookTitle.value,
          dto.applicant.value,
          "received",
          dto.requestedAt,
          null,
          dto.pic.value,
          dto.completedAt
        )
      }
  }
}