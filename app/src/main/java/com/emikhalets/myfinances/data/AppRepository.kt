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

class AppRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao,
    private val prefs: Prefs,
) {

    /**
     * Categories Dao
     */

    suspend fun getCategories(type: TransactionType): Result<Flow<List<Category>>> {
        return runDatabaseRequest { categoryDao.getAllOrderByName(type) }
    }

    suspend fun getCategory(id: Long): Result<Flow<Category>> {
        return runDatabaseRequest { categoryDao.getById(id) }
    }

    suspend fun insertCategory(category: Category): Result<Boolean> {
        return runDatabaseRequest { categoryDao.insertIfNotExist(category) }
    }

    suspend fun updateCategory(category: Category): Result<Int> {
        return runDatabaseRequest { categoryDao.update(category) }
    }

    suspend fun deleteCategory(category: Category): Result<Int> {
        return runDatabaseRequest { categoryDao.delete(category) }
    }


    /**
     * Transactions Dao
     */

    suspend fun getTransactions(type: TransactionType): Result<Flow<List<Transaction>>> {
        val walletId = prefs.currentWalletId
        return runDatabaseRequest { transactionDao.getAllOrderByTime(walletId, type) }
    }

    suspend fun getTransaction(id: Long): Result<Flow<Transaction>> {
        return runDatabaseRequest { transactionDao.getById(id) }
    }

    suspend fun insertTransaction(transaction: Transaction): Result<Long> {
        return runDatabaseRequest { transactionDao.insert(transaction) }
    }

    suspend fun updateTransaction(transaction: Transaction): Result<Int> {
        return runDatabaseRequest { transactionDao.update(transaction) }
    }

    suspend fun deleteTransaction(transaction: Transaction): Result<Int> {
        return runDatabaseRequest { transactionDao.delete(transaction) }
    }

    /**
     * Wallets Dao
     */

    suspend fun getWallets(): Result<Flow<List<Wallet>>> {
        return runDatabaseRequest { walletDao.getAllOrderByName() }
    }

    suspend fun getWallet(id: Long): Result<Flow<Wallet>> {
        return runDatabaseRequest { walletDao.getById(id) }
    }

    suspend fun insertWallet(wallet: Wallet): Result<Boolean> {
        return runDatabaseRequest { walletDao.insertIfNotExist(wallet) }
    }

    suspend fun updateWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest { walletDao.update(wallet) }
    }

    suspend fun deleteWallet(wallet: Wallet): Result<Int> {
        return runDatabaseRequest { walletDao.delete(wallet) }
    }
}