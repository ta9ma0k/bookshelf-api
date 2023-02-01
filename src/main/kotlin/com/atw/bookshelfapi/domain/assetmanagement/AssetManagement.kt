package com.atw.bookshelfapi.domain.assetmanagement

import com.atw.bookshelfapi.domain.asset.Asset
import com.atw.bookshelfapi.domain.book.BookId
import com.atw.bookshelfapi.domain.usageapplication.*
import com.atw.bookshelfapi.domain.user.UserId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException

class AssetManagement private constructor(
  val bookId: BookId,
  val assets: List<Asset>,
  val usageApplications: List<UsageApplication>
) {
  companion object {
    fun reconstruct(bookId: BookId, assets: List<Asset>, usageApplications: List<UsageApplication>) =
      AssetManagement(bookId, assets, usageApplications)
  }

  fun addAssets(ownerId: UserId): AssetManagement =
    AssetManagement(bookId, assets.map { it.clone() }.plus(Asset.create(ownerId)), usageApplications)

  fun createUsageApplication(applicantId: UserId, reason: Reason): AssetManagement {
    if (assets.isEmpty()) {
      throw IllegalStateException("This book is not available because assets does not exists. [$bookId]")
    }
    val newOne = UsageApplication.create(applicantId, reason)
    return AssetManagement(bookId, assets, usageApplications.map { it.clone() }.plus(newOne))
  }

  fun assign(usageApplicationId: UsageApplicationId, picId: UserId): AssetManagement {
    val application = usageApplications.filterIsInstance<PicNotAssigned>().find { it.getId() == usageApplicationId }
      ?: throw NotFoundException()
    val countAssetsOfPic = assets.count { it.ownerId == picId }
    val countAssignedApplication = usageApplications.filterIsInstance<PicAssigned>().count { it.picId == picId }
    if (countAssetsOfPic <= countAssignedApplication) {
      throw IllegalStateException("Can't assign pic because user does not have enough assets. [$bookId]")
    }
    return AssetManagement(
      bookId,
      assets,
      usageApplications.filter { it.getId() != application.getId() }.map { it.clone() }.plus(application.assign(picId))
    )
  }

  fun receive(usageApplicationId: UsageApplicationId, applicant: UserId): AssetManagement {
    val application = usageApplications.filterIsInstance<PicAssigned>().find { it.getId() == usageApplicationId }
      ?: throw NotFoundException()
    val asset = assets.find { it.ownerId == application.picId }
      ?: throw IllegalStateException("Available assets does not exists. [$bookId]")
    return AssetManagement(
      bookId,
      assets.filter { it.getId() != asset.getId() }.map { it.clone() }.plus(asset.changeOwner(applicant)),
      usageApplications.filter { it.getId() != usageApplicationId }.map { it.clone() }.plus(application.received())
    )
  }
}