package com.emikhalets.data.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity("transactions")
data class TransactionDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "currency_id") val currencyId: Long,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
)