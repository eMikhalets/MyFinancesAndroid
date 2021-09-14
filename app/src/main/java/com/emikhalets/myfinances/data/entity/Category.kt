package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") val categoryId: Long = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "type") var type: Int = TransactionType.None.value
)