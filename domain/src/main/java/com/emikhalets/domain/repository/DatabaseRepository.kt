package com.emikhalets.domain.repository

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    /**
     * Categories Table
     */

    suspend fun insertCategory(entity: CategoryEntity): Result<Long>
    suspend fun updateCategory(entity: CategoryEntity): Result<Int>
    suspend fun deleteCategory(entity: CategoryEntity): Result<Int>
    suspend fun getCategoryFlow(id: Long): Result<Flow<CategoryEntity>>
    suspend fun getCategoriesFlow(): Result<Flow<List<CategoryEntity>>>
    suspend fun getCategoriesByType(type: TransactionType): Result<Flow<List<CategoryEntity>>>

    /**
     * Transactions Table
     */

    suspend fun insertTransaction(entity: TransactionEntity): Result<Long>
    suspend fun updateTransaction(entity: TransactionEntity): Result<Int>
    suspend fun deleteTransaction(entity: TransactionEntity): Result<Int>
    suspend fun getTransactionFlow(id: Long): Result<Flow<TransactionEntity>>
    suspend fun getTransactionsFlow(): Result<Flow<List<TransactionEntity>>>

    /**
     * Currencies Table
     */

    suspend fun insertCurrency(entity: CurrencyEntity): Result<Long>
    suspend fun updateCurrency(entity: CurrencyEntity): Result<Int>
    suspend fun deleteCurrency(entity: CurrencyEntity): Result<Int>
    suspend fun getCurrencyFlow(id: Long): Result<Flow<CurrencyEntity>>
    suspend fun getCurrenciesFlow(): Result<Flow<List<CurrencyEntity>>>

    /**
     * Wallets Table
     */

    suspend fun insertWallet(entity: WalletEntity): Result<Long>
    suspend fun updateWallet(entity: WalletEntity): Result<Int>
    suspend fun deleteWallet(entity: WalletEntity): Result<Int>
    suspend fun getWalletFlow(id: Long): Result<Flow<WalletEntity>>
    suspend fun getWalletsFlow(): Result<Flow<List<WalletEntity>>>
}