package com.emikhalets.myfinances.data.entity

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getDefaultId

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TransactionType,
) {

    @Ignore
    constructor(name: String, type: TransactionType) : this(0, name, type)

    companion object {

        @Composable
        fun getDefaultOld(type: TransactionType? = null): Category {
            val name = stringResource(R.string.default_category_name)
            val typeNotNull = type ?: TransactionType.Expense
            val id = typeNotNull.getDefaultId()
            return Category(id, name, typeNotNull)
        }

        fun getDefaultOld(context: Context, type: TransactionType? = null): Category {
            val name = context.getString(R.string.default_category_name)
            val typeNotNull = type ?: TransactionType.Expense
            val id = typeNotNull.getDefaultId()
            return Category(id, name, typeNotNull)
        }

        fun getDefault(context: Context, type: TransactionType): Category {
            val id = type.getDefaultId()
            val name = context.getString(R.string.default_category_name)
            return Category(id, name, type)
        }
    }
}

fun List<Category>.withDefault(context: Context, type: TransactionType): List<Category> {
    val list = this.toMutableList()
    val name = context.getString(R.string.default_category_name)
    list.add(Category(type.getDefaultId(), name, type))
    return list
}