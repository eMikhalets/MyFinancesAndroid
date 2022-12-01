package com.emikhalets.myfinances.data.repository

import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Categories Dao
     */

    suspend fun insert(category: Category): Result<Long>

    suspend fun update(category: Category): Result<Int>

    suspend fun delete(category: Category): Result<Int>

    suspend fun getCategory(name: String): Result<Category>

    suspend fun getCategories(): Result<Flow<List<Category>>>

    suspend fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>>


    /**
     * Transactions Dao
     */

    suspend fun insert(transaction: Transaction): Result<Long>

    suspend fun update(transaction: Transaction): Result<Int>

    suspend fun delete(transaction: Transaction): Result<Int>

    suspend fun getTransaction(id: Long): Result<Transaction>

    suspend fun getTransactions(): Result<Flow<List<Transaction>>>

    suspend fun getTransactionsByCategory(category: String): Result<Flow<List<Transaction>>>

    suspend fun getTransactionsByType(type: TransactionType): Result<Flow<List<Transaction>>>
}