package com.emikhalets.myfinances.domain.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionEntity(
    @Embedded
    val transaction: Transaction,
    @Relation(parentColumn = "category_id", entityColumn = "id")
    val category: Category,
)