package com.atw.bookshelfapi.domain.common

data class EntityId<T>(val value: T)
open class EntityBase<T>(
  private val id: EntityId<T>?
) {

  fun getId(): EntityId<T> {
    if (id == null) {
      throw IllegalStateException()
    }
    return id

  }

  val hasId = id != null
}