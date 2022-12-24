package com.atw.bookshelfapi.usecase.command.usageapplication

import com.atw.bookshelfapi.domain.book.BookRepository
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.domain.usageapplication.UsageApplication
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class CreateUsageApplicationCommand(
  private val bookRepository: BookRepository,
  private val usageApplicationRepository: UsageApplicationRepository,
  private val userRepository: UserRepository
) {
  fun create(dto: CreateUsageApplicationDto): UsageApplicationId {
    val user = userRepository.findByEmail(dto.applicantUserEmail) ?: throw NotFoundException()
    if (bookRepository.findByIsbn(dto.isbn).isEmpty()) {
      throw NotFoundException()
    }
    val newUsageApplication = UsageApplication.create(user.getId(), dto.isbn)
    return usageApplicationRepository.save(newUsageApplication)
  }
}

data class CreateUsageApplicationDto(
  val applicantUserEmail: Email,
  val isbn: Isbn
)