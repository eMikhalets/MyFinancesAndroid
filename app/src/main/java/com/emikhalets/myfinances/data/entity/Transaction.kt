package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "transaction_id") val transactionId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "timestamp") var timestamp: Long
)