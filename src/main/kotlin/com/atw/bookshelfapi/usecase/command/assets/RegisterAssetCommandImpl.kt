package com.atw.bookshelfapi.usecase.command.assets

import com.atw.bookshelfapi.domain.asset.Asset
import com.atw.bookshelfapi.domain.asset.AssetId
import com.atw.bookshelfapi.domain.asset.AssetRepository
import com.atw.bookshelfapi.domain.book.Book
import com.atw.bookshelfapi.domain.book.BookRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class RegisterAssetsCommandImpl(
  private val assetRepository: AssetRepository,
  private val userRepository: UserRepository,
  private val bookRepository: BookRepository
) : RegisterAssetCommand {
  override fun exec(dto: RegisterAssetDto): AssetId {
    val user = userRepository.findByEmail(dto.ownerEmail) ?: throw NotFoundException()
    var bookId = bookRepository.findByIsbn(dto.isbn)?.getId()
    if (bookId == null) {
      val newBook = Book.create(dto.isbn, dto.title, dto.thumbnailUrl)
      bookId = bookRepository.save(newBook)
    }
    return assetRepository.save(Asset.create(bookId, user.getId()))
  }
}