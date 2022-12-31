package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.book.BookAggregateRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ReceiveUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val bookAggregateRepository: BookAggregateRepository
) : ReceiveUsageApplicationCommand {
  override fun exec(dto: ReceiveUsageApplicationCommandDto) {
    val applicant = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val bookAggregate = bookAggregateRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val newOne = bookAggregate.receiveUsageApplication(dto.usageApplicationId, applicant.getId())
    bookAggregateRepository.save(newOne)
  }
}