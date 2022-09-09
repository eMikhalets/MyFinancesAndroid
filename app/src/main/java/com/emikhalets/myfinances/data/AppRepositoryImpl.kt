package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.Prefs
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.runDatabaseRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao,
    private val prefs: Prefs,
) : AppRepository {

    /**
     * Categories Dao
     */

    override suspend fun getCategories(type: TransactionType): Result<Flow<List<Category>>> {
        return runDatabaseRequest { categoryDao.getAllOrderByName(type) }
    }

    override suspend fun getCategory(id: Long): Result<Flow<Category>> {
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
            transactions.map { it.copy(categoryId = -1) }
            transactionDao.updateAll(transactions)
            categoryDao.delete(category)
        }
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
        return runDatabaseRequest {
            transactionDao.delete(wallet.id)
            walletDao.delete(wallet)
        }
    }
}