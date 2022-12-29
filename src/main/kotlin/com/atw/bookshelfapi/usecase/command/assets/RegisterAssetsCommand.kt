package com.atw.bookshelfapi.usecase.command.assets

import com.atw.bookshelfapi.domain.asset.AssetId
import com.atw.bookshelfapi.domain.book.Isbn
import com.atw.bookshelfapi.domain.book.ThumbnailUrl
import com.atw.bookshelfapi.domain.book.Title
import com.atw.bookshelfapi.domain.user.Email

data class RegisterAssetDto(
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?,
  val ownerEmail: Email
)
interface RegisterAssetCommand {
  fun exec(dto: RegisterAssetDto): AssetId
}