package com.emikhalets.myfinances.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TransactionType,
) {

    fun hasDefaultId(): Boolean {
        return id == DEF_ID_EXPENSE || id == DEF_ID_INCOME
    }

    companion object {

        const val DEF_ID_INCOME = -11
        const val DEF_ID_EXPENSE = -10

        fun getDefaultInstance(type: TransactionType): Category {
            return Category(getDefaultId(type), "", type)
        }

        fun getDefaultId(type: TransactionType): Int {
            return when (type) {
                TransactionType.Expense -> DEF_ID_EXPENSE
                TransactionType.Income -> DEF_ID_INCOME
            }
        }
    }
}

fun Category?.copyOrNew(name: String, type: TransactionType): Category {
    return this?.copy(name = name) ?: Category(name = name, type = type)
}