package com.atw.bookshelfapi.domain.common

interface EntityId<T> {
  val value: T
}

open class EntityBase<T : EntityId<*>>(
  private val id: T?
) {

  fun getId(): T {
    if (id == null) {
      throw IllegalStateException()
    }
    return id

  }

  val hasId = id != null
}