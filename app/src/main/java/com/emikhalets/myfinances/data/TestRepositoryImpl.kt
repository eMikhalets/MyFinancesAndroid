package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestRepositoryImpl @Inject constructor() : AppRepository {

    /**
     * Categories Dao
     */

    private val categories = mutableListOf(
        Category(1, "Еда", TransactionType.Expense),
        Category(2, "Квартира", TransactionType.Expense),
        Category(3, "Интернет", TransactionType.Expense),
        Category(4, "Телефон", TransactionType.Expense),
        Category(5, "Транспорт", TransactionType.Expense),
        Category(6, "Развлечения", TransactionType.Expense),
        Category(7, "Зарплата", TransactionType.Income),
        Category(8, "Премия", TransactionType.Income),
        Category(9, "Подарок", TransactionType.Income),
        Category(10, "Подработка", TransactionType.Income)
    )

    override suspend fun getCategories(type: TransactionType): Result<Flow<List<Category>>> {
        val list = categories.filter { it.type == type }
        return Result.success(flowOf(list))
    }

    override suspend fun getCategory(id: Long): Result<Flow<Category>> {
        val item = categories.find { it.id == id }
        return item?.let { Result.success(flowOf(item)) }
            ?: Result.failure(Throwable("No category"))
    }

    override suspend fun insertCategory(category: Category): Result<Boolean> {
        val lastId = categories.lastOrNull()?.id ?: 0
        categories.add(category.copy(id = lastId + 1))
        return Result.success(true)
    }

    override suspend fun updateCategory(category: Category): Result<Int> {
        val item = categories.find { it.id == category.id }
        val index = categories.indexOf(item)
        categories[index] = category
        return Result.success(1)
    }

    override suspend fun deleteCategory(category: Category): Result<Int> {
        transactions
            .filter { it.categoryId == category.id }
            .map { it.copy(categoryId = -1) }
        categories.remove(category)
        return Result.success(1)
    }

    /**
     * Transactions Dao
     */

    private val transactions = mutableListOf(
        Transaction(1, 1, 1, 100.0, TransactionType.Expense, "Комментарий", Date().time),
        Transaction(2, 2, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(3, 3, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(4, 4, 1, 100.0, TransactionType.Expense, "Пельмени", Date().time),
        Transaction(5, 5, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(6, 6, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(7, 7, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(8, 8, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(9, 9, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(10, 10, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(11, 1, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(12, 2, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(13, 3, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(14, 4, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(15, 5, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(16, 6, 1, 100.0, TransactionType.Expense, "", Date().time),
        Transaction(17, 7, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(18, 8, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(19, 9, 1, 100.0, TransactionType.Income, "", Date().time),
        Transaction(20, 10, 1, 100.0, TransactionType.Income, "", Date().time),
    )

    override suspend fun getTransactions(): Result<Flow<List<Transaction>>> {
        return Result.success(flowOf(transactions))
    }

    override suspend fun getTransaction(id: Long): Result<Flow<Transaction>> {
        val item = transactions.find { it.id == id }
        return item?.let { Result.success(flowOf(item)) }
            ?: Result.failure(Throwable("No transaction"))
    }

    override suspend fun insertTransaction(transaction: Transaction): Result<Long> {
        val lastId = transactions.lastOrNull()?.id ?: 0
        transactions.add(transaction.copy(id = lastId + 1))
        return Result.success(lastId + 1)
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Int> {
        val item = transactions.find { it.id == transaction.id }
        val index = transactions.indexOf(item)
        transactions[index] = transaction
        return Result.success(1)
    }

    override suspend fun deleteTransaction(transaction: Transaction): Result<Int> {
        transactions.remove(transaction)
        return Result.success(1)
    }

    /**
     * Wallets Dao
     */

    private val wallets = mutableListOf(
        Wallet(1, "Основной"),
        Wallet(2, "Второстепенный"),
    )

    override suspend fun getWallets(): Result<Flow<List<Wallet>>> {
        return Result.success(flowOf(wallets))
    }

    override suspend fun getWallet(id: Long): Result<Flow<Wallet>> {
        val item = wallets.find { it.id == id }
        return item?.let { Result.success(flowOf(item)) }
            ?: Result.failure(Throwable("No wallet"))
    }

    override suspend fun insertWallet(wallet: Wallet): Result<Boolean> {
        val lastId = wallets.lastOrNull()?.id ?: 0
        wallets.add(wallet.copy(id = lastId + 1))
        return Result.success(true)
    }

    override suspend fun updateWallet(wallet: Wallet): Result<Int> {
        val item = wallets.find { it.id == wallet.id }
        val index = wallets.indexOf(item)
        wallets[index] = wallet
        return Result.success(1)
    }

    override suspend fun deleteWallet(wallet: Wallet): Result<Int> {
        transactions.removeIf { it.walletId == wallet.id }
        wallets.remove(wallet)
        return Result.success(1)
    }
}