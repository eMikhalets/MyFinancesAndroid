package com.emikhalets.myfinances.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey()
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TransactionType,
) {
    companion object {
        const val DEFAULT_NAME = ""
    }
}