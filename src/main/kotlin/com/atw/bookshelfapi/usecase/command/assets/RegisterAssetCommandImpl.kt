package com.atw.bookshelfapi.usecase.command.assets

import com.atw.bookshelfapi.domain.book.Book
import com.atw.bookshelfapi.domain.book.BookAggregateRepository
import com.atw.bookshelfapi.domain.book.BookRepository
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class RegisterAssetsCommandImpl(
  private val userRepository: UserRepository,
  private val bookRepository: BookRepository,
  private val bookAggregateRepository: BookAggregateRepository
) : RegisterAssetCommand {
  override fun exec(dto: RegisterAssetDto) {
    val user = userRepository.findByEmail(dto.ownerEmail) ?: throw NotFoundException()
    val book = bookRepository.findByIsbn(dto.isbn)
    if (book == null) {
      val newBook = Book.create(dto.isbn, dto.title, dto.thumbnailUrl)
      bookRepository.save(newBook)
    }
    val isbn = book?.isbn ?: dto.isbn
    val bookAggregate = bookAggregateRepository.findByIsbn(isbn) ?: throw NotFoundException()
    val addedAssetAggregate = bookAggregate.addAssets(user.getId())
    bookAggregateRepository.save(addedAssetAggregate)
  }
}