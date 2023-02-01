package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.assetmanagement.AssetManagementRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class CreateUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val assetManagementRepository: AssetManagementRepository
) : CreateUsageApplicationCommand {
  override fun exec(dto: CreateUsageApplicationDto) {
    val user = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val assetManagement = assetManagementRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val newOne = assetManagement.createUsageApplication(user.getId(), dto.reason)
    assetManagementRepository.save(newOne)
  }
}