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
    @ColumnInfo(name = "init_value") val initValue: Double,
) {

    @Ignore
    constructor(name: String, initValue: Double) : this(0, name, initValue)
}

fun Wallet?.copyOrNew(name: String, initValue: Double): Wallet {
    return this?.copy(name = name, initValue = initValue) ?: Wallet(name, initValue)
}