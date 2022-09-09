package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.runDatabaseRequest
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
        categories.remove(category)
        return Result.success(1)
    }

    /**
     * Transactions Dao
     */

    override suspend fun getTransactions(): Result<Flow<List<Transaction>>> {
        val walletId = prefs.currentWalletId
        return runDatabaseRequest { transactionDao.getAllOrderByTime(walletId) }
    }

    override suspend fun getTransaction(id: Long): Result<Flow<Transaction>> {
        return runDatabaseRequest { transactionDao.getById(id) }
    }

    override suspend fun insertTransaction(transaction: Transaction): Result<Long> {
        return runDatabaseRequest { transactionDao.insert(transaction) }
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Int> {
        return runDatabaseRequest { transactionDao.update(transaction) }
    }

    override suspend fun deleteTransaction(transaction: Transaction): Result<Int> {
        return runDatabaseRequest { transactionDao.delete(transaction) }
    }

    /**
     * Wallets Dao
     */

    override suspend fun getWallets(): Result<Flow<List<Wallet>>> {
        return runDatabaseRequest { walletDao.getAllOrderByName() }
    }

    override suspend fun getWallet(id: Long): Result<Flow<Wallet>> {
        return runDatabaseRequest { walletDao.getById(id) }
    }

    override suspend fun insertWallet(wallet: Wallet): Result<Boolean> {
        return runDatabaseRequest { walletDao.insertIfNotExist(wallet) }
    }

    override suspend fun updateWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest { walletDao.update(wallet) }
    }

    override suspend fun deleteWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest { walletDao.delete(wallet) }
    }
}