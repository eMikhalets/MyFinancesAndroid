package com.emikhalets.myfinances.data.repository

import com.emikhalets.myfinances.data.database.CategoryDao
import com.emikhalets.myfinances.data.database.TransactionDao
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.execute
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
) : AppRepository {

    /**
     * Categories Dao
     */

    override suspend fun insert(category: Category): Result<Long> {
        return execute { categoryDao.insert(category) }
    }

    override suspend fun update(category: Category): Result<Int> {
        return execute { categoryDao.update(category) }
    }

    override suspend fun delete(category: Category): Result<Int> {
        return execute {
            val transactions = transactionDao.getAllForDelete(category.name)
            val newTransactions = transactions.map { it.copy(category = Category.DEFAULT_NAME) }
            transactionDao.updateAll(newTransactions)
            categoryDao.delete(category)
        }
    }

    override suspend fun getCategory(name: String): Result<Category> {
        return execute { categoryDao.getItem(name) }
    }

    override suspend fun getCategories(): Result<Flow<List<Category>>> {
        return execute { categoryDao.getAll() }
    }

    override suspend fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>> {
        return execute { categoryDao.getAll(type) }
    }

    /**
     * Transactions Dao
     */

    override suspend fun insert(transaction: Transaction): Result<Long> {
        return execute { transactionDao.insert(transaction) }
    }

    override suspend fun update(transaction: Transaction): Result<Int> {
        return execute { transactionDao.update(transaction) }
    }

    override suspend fun delete(transaction: Transaction): Result<Int> {
        return execute { transactionDao.delete(transaction) }
    }

    override suspend fun getTransaction(id: Long): Result<Transaction> {
        return execute { transactionDao.getItem(id) }
    }

    override suspend fun getTransactions(): Result<Flow<List<Transaction>>> {
        return execute { transactionDao.getAll() }
    }

    override suspend fun getTransactionsByCategory(category: String): Result<Flow<List<Transaction>>> {
        return execute { transactionDao.getAll(category) }
    }

    override suspend fun getTransactionsByType(type: TransactionType): Result<Flow<List<Transaction>>> {
        return execute { transactionDao.getAll(type) }
    }
}