package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "transaction_id") val transactionId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "category_name") var categoryName: String,
    @ColumnInfo(name = "category_icon") var categoryIcon: Int,
    @ColumnInfo(name = "timestamp") var timestamp: Long
) {

    @Ignore
    constructor() : this(
        transactionId = 0,
        categoryId = 0,
        walletId = 0,
        amount = 0.0,
        type = 0,
        note = "",
        categoryName = "",
        categoryIcon = 0,
        timestamp = 0
    )

    @Ignore
    constructor(
        categoryId: Long,
        walletId: Long,
        amount: Double,
        type: Int,
        note: String,
        categoryName: String,
        categoryIcon: Int,
        timestamp: Long
    ) : this(
        transactionId = 0,
        categoryId = categoryId,
        walletId = walletId,
        amount = amount,
        type = type,
        note = note,
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        timestamp = timestamp
    )
}