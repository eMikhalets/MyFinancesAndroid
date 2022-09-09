package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "value") var value: Double,
    @ColumnInfo(name = "type") var type: TransactionType,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "timestamp") var timestamp: Long,
) {

    @Ignore
    constructor(
        categoryId: Long,
        walletId: Long,
        value: Double,
        type: TransactionType,
        note: String,
        timestamp: Long,
    ) : this(0, categoryId, walletId, value, type, note, timestamp)
}