package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TransactionType,
) {

    @Ignore
    constructor(name: String, type: TransactionType) : this(0, name, type)
}

fun Category?.copyOrNew(name: String, type: TransactionType): Category {
    return this?.copy(name = name) ?: Category(name, type)
}