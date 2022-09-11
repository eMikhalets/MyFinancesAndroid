package com.emikhalets.myfinances.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.myfinances.utils.enums.TransactionType
import java.util.*

data class TransactionEntity(
    @Embedded
    val transaction: Transaction,
    @Relation(parentColumn = "category_id", entityColumn = "id")
    val category: Category,
)

fun TransactionEntity?.copyTransactionOrNew(
    categoryId: Long,
    walletId: Long,
    value: Double,
    type: TransactionType,
    note: String,
): Transaction {
    val timestamp = this?.transaction?.timestamp ?: Date().time
    return this?.transaction?.copy(
        categoryId = categoryId,
        walletId = walletId,
        value = value,
        type = type,
        note = note,
        timestamp = timestamp
    ) ?: Transaction(categoryId, walletId, value, type, note, timestamp)
}