package com.emikhalets.data.repository

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.repository.DatabaseRepository
import com.emikhalets.myfinances.data.database.CategoryDao
import com.emikhalets.myfinances.data.database.TransactionDao
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.execute
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl @Inject constructor(
    private val categoriesDao: CategoryDao,
    private val transactionsDao: TransactionDao,
    private val walletsDao: TransactionDao,
    private val currenciesDao: TransactionDao,
) : DatabaseRepository {

    override suspend fun insert(category: Category): Result<Long> {
        return execute { categoryDao.insert(category) }
    }

    override suspend fun update(category: Category): Result<Int> {
        return execute { categoryDao.update(category) }
    }

    override suspend fun delete(category: Category): Result<Int> {
        return execute {
            val transactions = transactionDao.getAllForDelete(category.id)
            val newTransactions = transactions.map { transaction ->
                transaction.copy(categoryId = Category.getDefaultId(transaction.type))
            }
            transactionDao.updateAll(newTransactions)
            categoryDao.delete(category)
        }
    }

    override suspend fun getCategory(id: Int): Result<Category> {
        return execute { categoryDao.getItem(id) }
    }

    override suspend fun getCategories(): Result<Flow<List<Category>>> {
        return execute { categoryDao.getAll() }
    }

    /**
     * Categories Dao
     */

    override suspend fun insertCategory(entity: CategoryEntity): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategory(entity: CategoryEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(entity: CategoryEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryFlow(id: Long): Result<Flow<CategoryEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesFlow(): Result<Flow<List<CategoryEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>> {
        return execute { categoryDao.getAll(type) }
    }

    override suspend fun insertTransaction(entity: TransactionEntity): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(entity: TransactionEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(entity: TransactionEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionFlow(id: Long): Result<Flow<TransactionEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionsFlow(): Result<Flow<List<TransactionEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrency(entity: CurrencyEntity): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrency(entity: CurrencyEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrency(entity: CurrencyEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyFlow(id: Long): Result<Flow<CurrencyEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrenciesFlow(): Result<Flow<List<CurrencyEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertWallet(entity: WalletEntity): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun updateWallet(entity: WalletEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWallet(entity: WalletEntity): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getWalletFlow(id: Long): Result<Flow<WalletEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWalletsFlow(): Result<Flow<List<WalletEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesByTypeSync(type: TransactionType): Result<List<Category>> {
        return execute { categoryDao.getAllSync(type) }
    }

    override suspend fun insert(transaction: Transaction): Result<Long> {
        return execute { transactionDao.insert(transaction) }
    }

    override suspend fun update(transaction: Transaction): Result<Int> {
        return execute { transactionDao.update(transaction) }
    }

    override suspend fun delete(transaction: Transaction): Result<Int> {
        return execute { transactionDao.delete(transaction) }
    }

    override suspend fun getTransaction(id: Long): Result<TransactionEntity> {
        return execute { transactionDao.getItem(id) }
    }

    override suspend fun getTransactions(): Result<Flow<List<TransactionEntity>>> {
        return execute { transactionDao.getAll() }
    }

    override suspend fun getTransactionsByCategory(categoryId: Int): Result<Flow<List<TransactionEntity>>> {
        return execute { transactionDao.getAll(categoryId) }
    }

    override suspend fun getTransactionsByType(type: TransactionType): Result<Flow<List<TransactionEntity>>> {
        return execute { transactionDao.getAll(type) }
    }
}