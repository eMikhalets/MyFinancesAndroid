package com.emikhalets.myfinances.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "icon") var icon: Int
) {

    @Ignore
    constructor(name: String, type: Int, icon: Int) : this(0, name, type, icon)
}