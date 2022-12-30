package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.asset.AssetRepository
import com.atw.bookshelfapi.domain.book.BookRepository
import com.atw.bookshelfapi.domain.usageapplication.UsageApplication
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class CreateUsageApplicationCommandImpl(
  private val usageApplicationRepository: UsageApplicationRepository,
  private val userRepository: UserRepository,
  private val bookRepository: BookRepository,
  private val assetRepository: AssetRepository
) : CreateUsageApplicationCommand {
  override fun exec(dto: CreateUsageApplicationDto): UsageApplicationId {
    val user = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val book = bookRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val assets = assetRepository.findByBook(book.getId())
    if (assets.isEmpty()) {
      throw IllegalStateException("Assets not exists. [${book.isbn}]")
    }
    val newApplication = UsageApplication.create(user.getId(), book.getId())
    return usageApplicationRepository.save(newApplication)
  }
}