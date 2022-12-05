package com.emikhalets.myfinances.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType
import java.util.*

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo(name = "timestamp") val timestamp: Long = Date().time,
)

fun Transaction?.copyOrNew(
    categoryId: Int,
    value: Double,
    type: TransactionType,
    note: String,
): Transaction {
    return this?.copy(
        categoryId = categoryId,
        value = value,
        type = type,
        note = note
    ) ?: Transaction(
        categoryId = categoryId,
        value = value,
        type = type,
        note = note,
        timestamp = Date().time
    )
}