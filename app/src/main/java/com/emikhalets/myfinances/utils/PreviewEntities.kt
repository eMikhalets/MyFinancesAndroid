package com.emikhalets.myfinances.utils

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.data.entity.WalletEntity
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

    fun getCategoriesScreenIncomeList(): List<Category> {
        return listOf(
            Category("Категория 7", TransactionType.Income),
            Category("Категория 8", TransactionType.Income),
            Category("Категория 9", TransactionType.Income),
            Category("Категория 10", TransactionType.Income)
        )
    }

    fun getCategoriesScreenExpenseList(): List<Category> {
        return listOf(
            Category("Категория 1", TransactionType.Expense),
            Category("Категория 2", TransactionType.Expense),
            Category("Категория 3", TransactionType.Expense),
            Category("Категория 4", TransactionType.Expense),
            Category("Категория 5", TransactionType.Expense),
            Category("Категория 6", TransactionType.Expense)
        )
    }

    fun getWalletsScreenList(): List<WalletEntity> {
        return listOf(
            WalletEntity(Wallet("Кошелек 1", 100.00), 900.21),
            WalletEntity(Wallet("Кошелек 2", 1_000.00), 900.20),
            WalletEntity(Wallet("Кошелек 3", 10_000.00), 9000.20),
            WalletEntity(Wallet("Кошелек 4", 100_000.00), 90000.20),
            WalletEntity(Wallet("Кошелек 5", 1_000_000.00), 900000.20),
            WalletEntity(Wallet("Кошелек 6", 10_000_000.00), 900.20)
        )
    }
}