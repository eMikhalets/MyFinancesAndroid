package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType
import java.util.*

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id") val transactionId: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long = 0,
    @ColumnInfo(name = "wallet_id") val walletId: Long = 0,
    @ColumnInfo(name = "value") var value: Double = 0.0,
    @ColumnInfo(name = "type") var type: Int = TransactionType.None.value,
    @ColumnInfo(name = "note") var note: String = "",
    @ColumnInfo(name = "timestamp") var timestamp: Long = Date().time
)