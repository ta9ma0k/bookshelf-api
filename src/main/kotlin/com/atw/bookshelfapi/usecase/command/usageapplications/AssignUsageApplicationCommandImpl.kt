package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.assetmanagement.AssetManagementRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class AssignUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val bookAggregateRepository: AssetManagementRepository
) : AssignUsageApplicationCommand {
  override fun exec(dto: AssignUsageApplicationCommandDto) {
    val pic = userRepository.findByEmail(dto.picEmail) ?: throw NotFoundException()
    val assetManagement = bookAggregateRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val newOne = assetManagement.assign(dto.usageApplicationId, pic.getId())
    bookAggregateRepository.save(newOne)
  }
}