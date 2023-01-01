package com.atw.bookshelfapi.domain.book

import com.atw.bookshelfapi.domain.asset.Asset
import com.atw.bookshelfapi.domain.usageapplication.*
import com.atw.bookshelfapi.domain.user.UserId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException

//TODO 名前が良くない
class BookAggregate private constructor(
  val bookId: BookId,
  val assets: List<Asset>,
  val usageApplications: List<UsageApplication>
) {
  companion object {
    fun reconstruct(bookId: BookId, assets: List<Asset>, usageApplications: List<UsageApplication>) =
      BookAggregate(bookId, assets, usageApplications)
  }

  fun addAssets(ownerId: UserId): BookAggregate =
    BookAggregate(bookId, assets.plus(Asset.create(ownerId)), usageApplications)

  fun createUsageApplication(applicantId: UserId, reason: Reason): BookAggregate {
    if (assets.isEmpty()) {
      throw IllegalStateException("This book is not available, because assets doe's not exists. [$bookId]")
    }
    val newOne = UsageApplication.create(applicantId, reason)
    return BookAggregate(bookId, assets, usageApplications.plus(newOne))
  }

  fun assignUsageApplication(usageApplicationId: UsageApplicationId, picId: UserId): BookAggregate {
    val application = usageApplications.filterIsInstance<PicNotAssigned>().find { it.getId() == usageApplicationId }
      ?: throw NotFoundException()
    val countAssetsOfPic = assets.count { it.ownerId == picId }
    val countAssignedApplication = usageApplications.filterIsInstance<PicAssigned>().count { it.picId == picId }
    if (countAssetsOfPic <= countAssignedApplication) {
      throw IllegalStateException("Can't assign pic because available assets doe's not exists. [$bookId]")
    }
    return BookAggregate(
      bookId,
      assets,
      usageApplications.filter { it.getId() != application.getId() }.plus(application.assign(picId))
    )
  }

  fun receiveUsageApplication(usageApplicationId: UsageApplicationId, applicant: UserId): BookAggregate {
    val application = usageApplications.filterIsInstance<PicAssigned>().find { it.getId() == usageApplicationId }
      ?: throw NotFoundException()
    val asset = assets.find { it.ownerId == application.picId }
      ?: throw IllegalStateException("Available assets doe's not exists. [$bookId]")
    return BookAggregate(
      bookId,
      assets.filter { it.getId() != asset.getId() }.plus(asset.changeOwner(applicant)),
      usageApplications.filter { it.getId() != usageApplicationId }.plus(application.received())
    )
  }
}