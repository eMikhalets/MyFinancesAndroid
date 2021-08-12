package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao
) {

    // ========== Categories Dao ==========

    suspend fun getCategories(type: Int): Result<List<Category>> {
        return try {
            Result.Success(categoryDao.getAllByType(type))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    // ========== Transactions Dao ==========

    suspend fun getTransactions(): Result<List<Transaction>> {
        return try {
            Result.Success(transactionDao.getAll())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    suspend fun insertTransaction(transaction: Transaction): Result<Long> {
        return try {
            val result = transactionDao.insert(transaction)
            Result.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    // ========== Wallets Dao ==========

    suspend fun getWallets(): Result<List<Wallet>> {
        return try {
            Result.Success(walletDao.getAll())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    suspend fun insertWallet(wallet: Wallet): Result<Long> {
        return try {
            Result.Success(walletDao.insert(wallet))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }
}