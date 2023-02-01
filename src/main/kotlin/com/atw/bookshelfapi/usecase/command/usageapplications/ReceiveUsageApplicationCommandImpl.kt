package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.assetmanagement.AssetManagementRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ReceiveUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val assetManagementRepository: AssetManagementRepository
) : ReceiveUsageApplicationCommand {
  override fun exec(dto: ReceiveUsageApplicationCommandDto) {
    val applicant = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val assetManagement = assetManagementRepository.findByIsbn(dto.isbn) ?: throw NotFoundException()
    val newOne = assetManagement.receive(dto.usageApplicationId, applicant.getId())
    assetManagementRepository.save(newOne)
  }
}