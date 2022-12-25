package com.atw.bookshelfapi.usecase.command.books

import com.atw.bookshelfapi.domain.book.*
import com.atw.bookshelfapi.domain.user.Email
import com.atw.bookshelfapi.domain.user.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class RegisterBookCommand(
  private val userRepository: UserRepository,
  private val bookRepository: BookRepository
) {
  fun exec(dto: RegisterBookDto): BookId {
    val user = userRepository.findByEmail(dto.ownerEmail) ?: throw NotFoundException()
    val newBook = Book.create(user.getId(), dto.isbn, dto.title, dto.thumbnailUrl)
    return bookRepository.save(newBook)
  }
}

data class RegisterBookDto(
  val ownerEmail: Email,
  val isbn: Isbn,
  val title: Title,
  val thumbnailUrl: ThumbnailUrl?
)