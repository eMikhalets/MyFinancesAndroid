package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "wallet_id") val walletId: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Double
)