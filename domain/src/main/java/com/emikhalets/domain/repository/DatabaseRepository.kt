package com.emikhalets.domain.repository

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    /**
     * Categories Table
     */

    suspend fun insertCategory(entity: CategoryEntity): ResultWrapper<Long>
    suspend fun updateCategory(entity: CategoryEntity): ResultWrapper<Int>
    suspend fun deleteCategory(entity: CategoryEntity): ResultWrapper<Int>
    suspend fun getCategoryFlow(id: Long): ResultWrapper<Flow<CategoryEntity>>
    suspend fun getCategoriesFlow(): ResultWrapper<Flow<List<CategoryEntity>>>
    suspend fun getCategoriesFlow(type: TransactionType): ResultWrapper<Flow<List<CategoryEntity>>>

    /**
     * Transactions Table
     */

    suspend fun insertTransaction(entity: TransactionEntity): ResultWrapper<Long>
    suspend fun updateTransaction(entity: TransactionEntity): ResultWrapper<Int>
    suspend fun deleteTransaction(entity: TransactionEntity): ResultWrapper<Int>
    suspend fun getTransactionFlow(id: Long): ResultWrapper<Flow<TransactionEntity>>
    suspend fun getTransactionsFlow(
        type: TransactionType,
        walletId: Long,
    ): ResultWrapper<Flow<List<ComplexTransactionEntity>>>

    /**
     * Currencies Table
     */

    suspend fun insertCurrency(entity: CurrencyEntity): ResultWrapper<Long>
    suspend fun updateCurrency(entity: CurrencyEntity): ResultWrapper<Int>
    suspend fun deleteCurrency(entity: CurrencyEntity): ResultWrapper<Int>
    suspend fun getCurrencyFlow(id: Long): ResultWrapper<Flow<CurrencyEntity>>
    suspend fun getCurrenciesFlow(): ResultWrapper<Flow<List<CurrencyEntity>>>

    /**
     * Wallets Table
     */

    suspend fun insertWallet(entity: WalletEntity): ResultWrapper<Long>
    suspend fun updateWallet(entity: WalletEntity): ResultWrapper<Int>
    suspend fun deleteWallet(entity: WalletEntity): ResultWrapper<Int>
    suspend fun getWalletFlow(id: Long): ResultWrapper<Flow<WalletEntity>>
    suspend fun getWalletsFlow(): ResultWrapper<Flow<List<WalletEntity>>>
    suspend fun getComplexWallets(): ResultWrapper<Flow<List<ComplexWalletEntity>>>
}