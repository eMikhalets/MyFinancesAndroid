package com.emikhalets.myfinances.data.repository

import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Categories Dao
     */

    suspend fun insert(category: Category): Result<Long>

    suspend fun update(category: Category): Result<Int>

    suspend fun delete(category: Category): Result<Int>

    suspend fun getCategory(id: Int): Result<Category>

    suspend fun getCategories(): Result<Flow<List<Category>>>

    suspend fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>>

    suspend fun getCategoriesByTypeSync(type: TransactionType): Result<List<Category>>

    /**
     * Transactions Dao
     */

    suspend fun insert(transaction: Transaction): Result<Long>

    suspend fun update(transaction: Transaction): Result<Int>

    suspend fun delete(transaction: Transaction): Result<Int>

    suspend fun getTransaction(id: Long): Result<TransactionEntity>

    suspend fun getTransactions(): Result<Flow<List<TransactionEntity>>>

    suspend fun getTransactionsByCategory(categoryId: Int): Result<Flow<List<TransactionEntity>>>

    suspend fun getTransactionsByType(type: TransactionType): Result<Flow<List<TransactionEntity>>>
}