package com.atw.bookshelfapi.usecase.command.usageapplications

import com.atw.bookshelfapi.domain.asset.AssetRepository
import com.atw.bookshelfapi.domain.usageapplication.PicAssigned
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationId
import com.atw.bookshelfapi.domain.usageapplication.UsageApplicationRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ReceiveUsageApplicationCommandImpl(
  private val userRepository: UserRepository,
  private val applicationRepository: UsageApplicationRepository,
  private val assetRepository: AssetRepository
) : ReceiveUsageApplicationCommand {
  override fun exec(dto: ReceiveUsageApplicationCommandDto): UsageApplicationId {
    val applicant = userRepository.findByEmail(dto.applicantEmail) ?: throw NotFoundException()
    val usageApplication = applicationRepository.findById(dto.usageApplicationId) ?: throw NotFoundException()
    //check application is PicAssigned
    if (usageApplication !is PicAssigned) {
      throw IllegalStateException()
    }
    //check applicant user
    if (usageApplication.applicantId != applicant.getId()) {
      throw IllegalStateException()
    }
    val assets = assetRepository.findByBook(usageApplication.bookId)
    //check pic has book
    val assetOfPic = assets.find { it.userId == usageApplication.picId } ?: throw IllegalStateException()
    val assetUpdatedOwner = assetOfPic.changeOwner(applicant.getId())
    assetRepository.save(assetUpdatedOwner)
    val receivedUsageApplication = usageApplication.received()
    //update asset owner
    return applicationRepository.save(receivedUsageApplication)
  }
}