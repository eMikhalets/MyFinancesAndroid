package com.emikhalets.data.repository

import com.emikhalets.core.UiString
import com.emikhalets.data.database.dao.CategoriesDao
import com.emikhalets.data.database.dao.CurrenciesDao
import com.emikhalets.data.database.dao.TransactionsDao
import com.emikhalets.data.database.dao.WalletsDao
import com.emikhalets.data.mapper.DatabaseMapper
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepositoryImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val transactionsDao: TransactionsDao,
    private val walletsDao: WalletsDao,
    private val currenciesDao: CurrenciesDao,
    private val mapper: DatabaseMapper,
) : DatabaseRepository {

    private suspend fun <T> execute(block: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(block())
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error(UiString.create(e.message))
        }
    }

//    override suspend fun delete(category: Category): ResultWrapper<Int> {
//        return execute {
//            val transactions = transactionDao.getAllForDelete(category.id)
//            val newTransactions = transactions.map { transaction ->
//                transaction.copy(categoryId = Category.getDefaultId(transaction.type))
//            }
//            transactionDao.updateAll(newTransactions)
//            categoryDao.delete(category)
//        }
//    }

    /**
     * Categories Table
     */

    override suspend fun insertCategory(entity: CategoryEntity): ResultWrapper<Long> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute { categoriesDao.insert(dbEntity) }
    }

    override suspend fun updateCategory(entity: CategoryEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute { categoriesDao.update(dbEntity) }
    }

    override suspend fun deleteCategory(entity: CategoryEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute { categoriesDao.delete(dbEntity) }
    }

    override suspend fun getCategoryFlow(id: Long): ResultWrapper<Flow<CategoryEntity>> {
        return execute {
            val flow = categoriesDao.getItemFlow(id)
            flow.map { mapper.mapCategoryDbToEntity(it) }
        }
    }

    override suspend fun getCategoriesFlow(): ResultWrapper<Flow<List<CategoryEntity>>> {
        return execute {
            val flow = categoriesDao.getAllFlow()
            flow.map { mapper.mapCategoriesListDbToEntity(it) }
        }
    }

    /**
     * Transactions Table
     */

    override suspend fun insertTransaction(entity: TransactionEntity): ResultWrapper<Long> {
        val dbEntity = mapper.mapTransactionEntityToDb(entity)
        return execute { transactionsDao.insert(dbEntity) }
    }

    override suspend fun updateTransaction(entity: TransactionEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapTransactionEntityToDb(entity)
        return execute { transactionsDao.update(dbEntity) }
    }

    override suspend fun deleteTransaction(entity: TransactionEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapTransactionEntityToDb(entity)
        return execute { transactionsDao.delete(dbEntity) }
    }

    override suspend fun getTransactionFlow(id: Long): ResultWrapper<Flow<TransactionEntity>> {
        return execute {
            val flow = transactionsDao.getItemFlow(id)
            flow.map { mapper.mapTransactionDbToEntity(it) }
        }
    }

    override suspend fun getTransactionsFlow(): ResultWrapper<Flow<List<TransactionEntity>>> {
        return execute {
            val flow = transactionsDao.getAllFlow()
            flow.map { mapper.mapTransactionsListDbToEntity(it) }
        }
    }

    /**
     * Currencies Table
     */

    override suspend fun insertCurrency(entity: CurrencyEntity): ResultWrapper<Long> {
        val dbEntity = mapper.mapCurrencyEntityToDb(entity)
        return execute { currenciesDao.insert(dbEntity) }
    }

    override suspend fun updateCurrency(entity: CurrencyEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCurrencyEntityToDb(entity)
        return execute { currenciesDao.update(dbEntity) }
    }

    override suspend fun deleteCurrency(entity: CurrencyEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCurrencyEntityToDb(entity)
        return execute { currenciesDao.delete(dbEntity) }
    }

    override suspend fun getCurrencyFlow(id: Long): ResultWrapper<Flow<CurrencyEntity>> {
        return execute {
            val flow = currenciesDao.getItemFlow(id)
            flow.map { mapper.mapCurrencyDbToEntity(it) }
        }
    }

    override suspend fun getCurrenciesFlow(): ResultWrapper<Flow<List<CurrencyEntity>>> {
        return execute {
            val flow = currenciesDao.getAllFlow()
            flow.map { mapper.mapCurrenciesListDbToEntity(it) }
        }
    }

    /**
     * Wallets Table
     */

    override suspend fun insertWallet(entity: WalletEntity): ResultWrapper<Long> {
        val dbEntity = mapper.mapWalletEntityToDb(entity)
        return execute { walletsDao.insert(dbEntity) }
    }

    override suspend fun updateWallet(entity: WalletEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapWalletEntityToDb(entity)
        return execute { walletsDao.update(dbEntity) }
    }

    override suspend fun deleteWallet(entity: WalletEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapWalletEntityToDb(entity)
        return execute { walletsDao.delete(dbEntity) }
    }

    override suspend fun getWalletFlow(id: Long): ResultWrapper<Flow<WalletEntity>> {
        return execute {
            val flow = walletsDao.getItemFlow(id)
            flow.map { mapper.mapWalletDbToEntity(it) }
        }
    }

    override suspend fun getWalletsFlow(): ResultWrapper<Flow<List<WalletEntity>>> {
        return execute {
            val flow = walletsDao.getAllFlow()
            flow.map { mapper.mapWalletsListDbToEntity(it) }
        }
    }
}