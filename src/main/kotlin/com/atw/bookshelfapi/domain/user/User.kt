package com.atw.bookshelfapi.domain.user

import com.atw.bookshelfapi.domain.common.EntityBase
import com.atw.bookshelfapi.domain.common.EntityId

typealias UserId = EntityId<Long>

class User private constructor(
  id: UserId?,
  val name: Username,
  val email: Email
) : EntityBase<Long>(id) {
  companion object {
    fun of(id: UserId, name: Username, email: Email) = User(id, name, email)
  }
}