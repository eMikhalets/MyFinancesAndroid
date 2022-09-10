package com.emikhalets.myfinances.utils

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.utils.enums.TransactionType
import java.util.*

object PreviewEntities {

    fun getMainScreenIncomeList(): List<TransactionEntity> {
        return listOf(
            TransactionEntity(
                Transaction(7, 1, 100.0, TransactionType.Income, "Комментарий", Date().time),
                Category("Категория 7", TransactionType.Income)),
            TransactionEntity(
                Transaction(8, 1, 200.0, TransactionType.Income, "Комментарий", Date().time),
                Category("Категория 8", TransactionType.Income)),
            TransactionEntity(
                Transaction(9, 1, 300.0, TransactionType.Income, "Комментарий", Date().time),
                Category("Категория 9", TransactionType.Income)),
            TransactionEntity(
                Transaction(10, 1, 400.0, TransactionType.Income, "Комментарий", Date().time),
                Category("Категория 10", TransactionType.Income))
        )
    }

    fun getMainScreenExpenseList(): List<TransactionEntity> {
        return listOf(
            TransactionEntity(
                Transaction(1, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 1", TransactionType.Expense)),
            TransactionEntity(
                Transaction(2, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 2", TransactionType.Expense)),
            TransactionEntity(
                Transaction(3, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 3", TransactionType.Expense)),
            TransactionEntity(
                Transaction(4, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 4", TransactionType.Expense)),
            TransactionEntity(
                Transaction(5, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 5", TransactionType.Expense)),
            TransactionEntity(
                Transaction(6, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
                Category("Категория 6", TransactionType.Expense))
        )
    }

    fun getTransactionScreenEntity(): TransactionEntity {
        return TransactionEntity(
            Transaction(1, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
            Category("Категория 1", TransactionType.Expense)
        )
    }
}