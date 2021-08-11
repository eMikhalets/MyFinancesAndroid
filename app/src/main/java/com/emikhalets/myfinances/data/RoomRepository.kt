package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao
) {

    suspend fun getCategories(type: Int): Result<List<Category>> {
        return try {
            Result.Success(categoryDao.getAllByType(type))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    suspend fun getTransactions(): Result<List<Transaction>> {
        return try {
            Result.Success(transactionDao.getAll())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex)
        }
    }
}