package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Categories Dao
     */

    suspend fun getCategories(): Result<Flow<List<Category>>>

    suspend fun getCategories(type: TransactionType): Result<Flow<List<Category>>>

    suspend fun getCategory(id: Long): Result<Category>

    suspend fun insertCategory(category: Category): Result<Boolean>

    suspend fun updateCategory(category: Category): Result<Int>

    suspend fun deleteCategory(category: Category): Result<Int>


    /**
     * Transactions Dao
     */

    suspend fun getTransactions(): Result<Flow<List<TransactionEntity>>>

    suspend fun getTransaction(id: Long): Result<TransactionEntity>

    suspend fun insertTransaction(transaction: Transaction): Result<Long>

    suspend fun updateTransaction(transaction: Transaction): Result<Int>

    suspend fun deleteTransaction(transaction: Transaction): Result<Int>

    /**
     * Wallets Dao
     */

    suspend fun getWallets(): Result<Flow<List<Wallet>>>

    suspend fun getWallet(id: Long): Result<Wallet>

    suspend fun insertWallet(wallet: Wallet): Result<Boolean>

    suspend fun updateWallet(wallet: Wallet): Result<Int>

    suspend fun deleteWallet(wallet: Wallet): Result<Int>
}