package com.atw.bookshelfapi.domain.user

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId

data class UserId(override val value: Long) : EntityId<Long>

class User private constructor(
  id: UserId?,
  val name: Username,
  val email: Email
) : EntityBase<UserId>(id) {
  companion object {
    fun reconstruct(id: UserId, name: Username, email: Email) = User(id, name, email)
  }
}