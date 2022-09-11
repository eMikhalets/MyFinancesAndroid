package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
) {

    @Ignore
    constructor(name: String) : this(0, name)
}

fun Wallet?.copyOrNew(name: String): Wallet {
    return this?.copy(name = name) ?: Wallet(name)
}