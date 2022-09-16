package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.data.entity.WalletEntity
import com.emikhalets.myfinances.utils.Prefs
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getDefaultId
import com.emikhalets.myfinances.utils.runDatabaseRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao,
    private val prefs: Prefs,
) : AppRepository {

    /**
     * Categories Dao
     */

    override suspend fun getCategories(): Result<Flow<List<Category>>> {
        return runDatabaseRequest { categoryDao.getAllOrderByName() }
    }

    override suspend fun getCategories(type: TransactionType): Result<Flow<List<Category>>> {
        return runDatabaseRequest { categoryDao.getAllOrderByName() }
    }

    override suspend fun getCategory(id: Long): Result<Category> {
        return runDatabaseRequest { categoryDao.getById(id) }
    }

    override suspend fun insertCategory(category: Category): Result<Boolean> {
        return runDatabaseRequest { categoryDao.insertIfNotExist(category) }
    }

    override suspend fun updateCategory(category: Category): Result<Int> {
        return runDatabaseRequest { categoryDao.update(category) }
    }

    override suspend fun deleteCategory(category: Category): Result<Int> {
        return runDatabaseRequest {
            val transactions = transactionDao.getAll(prefs.currentWalletId, category.id)
            val defaultId = category.type.getDefaultId()
            transactions.map { it.copy(categoryId = defaultId) }
            transactionDao.updateAll(transactions)
            categoryDao.delete(category)
        }
    }


    /**
     * Transactions Dao
     */

    override suspend fun getTransactions(): Result<Flow<List<TransactionEntity>>> {
        val walletId = prefs.currentWalletId
        return runDatabaseRequest { transactionDao.getAllOrderByTime(walletId) }
    }

    override suspend fun getTransaction(id: Long): Result<TransactionEntity> {
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

    override suspend fun getWallets(): Result<Flow<List<WalletEntity>>> {
        return runDatabaseRequest {
            walletDao.getAllOrderByName().map { list ->
                list.map { wallet ->
                    val sum = transactionDao.getAll(wallet.id)
                        .map { transaction -> defineValue(transaction) }
                        .reduceOrNull { acc, value -> acc + value } ?: 0
                        .plus(wallet.initValue)
                    WalletEntity(wallet, sum)
                }
            }
        }
    }

    private suspend fun defineValue(transaction: Transaction): Double {
        return when (categoryDao.getTypeById(transaction.categoryId)) {
            TransactionType.Expense -> transaction.value * -1
            TransactionType.Income -> transaction.value
        }
    }

    override suspend fun getWallet(id: Long): Result<Wallet> {
        return runDatabaseRequest { walletDao.getById(id) }
    }

    override suspend fun insertWallet(wallet: Wallet): Result<Long> {
        return runDatabaseRequest { walletDao.insertIfNotExist(wallet) }
    }

    override suspend fun updateWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest { walletDao.update(wallet) }
    }

    override suspend fun deleteWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest {
            transactionDao.delete(wallet.id)
            walletDao.delete(wallet)
        }
    }
}