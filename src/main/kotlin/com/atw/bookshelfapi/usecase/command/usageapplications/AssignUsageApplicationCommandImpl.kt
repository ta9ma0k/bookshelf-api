package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.asset.AssetRepository
import com.atw.bookshelfapi.domain.usageapplication.PicNotAssigned
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class AssignUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val usageApplicationRepository: UsageApplicationRepository,
  private val assetRepository: AssetRepository
) : AssignUsageApplicationCommand {
  override fun exec(dto: AssignUsageApplicationCommandDto): UsageApplicationId {
    val user = userRepository.findByEmail(dto.picEmail) ?: throw NotFoundException()
    //check application is not assigned pic
    val usageApplication = usageApplicationRepository.findById(dto.usageApplicationId) ?: throw NotFoundException()
    if (usageApplication !is PicNotAssigned) {
      throw IllegalStateException()
    }
    //check user has book
    val assets = assetRepository.findByBook(usageApplication.bookId)
    if (!assets.map { it.userId }.contains(user.getId())) {
      throw IllegalStateException()
    }
    val picAssignedUsageApplication = usageApplication.assign(user.getId())
    return usageApplicationRepository.save(picAssignedUsageApplication)
  }
}