package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.book.BookAggregateRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class CreateUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val bookAggregateRepository: BookAggregateRepository
) : CreateUsageApplicationCommand {
  override fun exec(dto: CreateUsageApplicationDto) {
    val user = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val bookAggregate = bookAggregateRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val newOne = bookAggregate.createUsageApplication(user.getId(), dto.reason)
    bookAggregateRepository.save(newOne)
  }
}