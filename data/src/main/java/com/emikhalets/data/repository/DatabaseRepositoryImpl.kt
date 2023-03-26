package com.emikhalets.data.repository

import com.emikhalets.core.CATEGORY_EXPENSE_ID
import com.emikhalets.core.CATEGORY_INCOME_ID
import com.emikhalets.core.CODE_CATEGORY_EXISTED
import com.emikhalets.core.CODE_DELETE_CURRENCY_TRANSACTIONS
import com.emikhalets.core.CURRENCY_ID
import com.emikhalets.core.UiString
import com.emikhalets.core.WALLET_ID
import com.emikhalets.data.CATEGORY_EXISTED
import com.emikhalets.data.DELETE_CURRENCY_TRANSACTIONS
import com.emikhalets.data.database.dao.CategoriesDao
import com.emikhalets.data.database.dao.CurrenciesDao
import com.emikhalets.data.database.dao.TransactionsDao
import com.emikhalets.data.database.dao.WalletsDao
import com.emikhalets.data.mapper.DatabaseMapper
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import com.emikhalets.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val transactionsDao: TransactionsDao,
    private val walletsDao: WalletsDao,
    private val currenciesDao: CurrenciesDao,
    private val mapper: DatabaseMapper,
) : DatabaseRepository {

    private suspend fun <T : Any> execute(block: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(block())
        } catch (e: Exception) {
            e.printStackTrace()
            when (e.message) {
                DELETE_CURRENCY_TRANSACTIONS -> {
                    ResultWrapper.Error(UiString.create(), CODE_DELETE_CURRENCY_TRANSACTIONS)
                }
                CATEGORY_EXISTED -> {
                    ResultWrapper.Error(UiString.create(), CODE_CATEGORY_EXISTED)
                }
                else -> {
                    ResultWrapper.Error(UiString.create(e.message))
                }
            }
        }
    }

    /**
     * Categories Table
     */

    override suspend fun insertCategory(entity: CategoryEntity): ResultWrapper<Long> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute {
            val isNameExisted = categoriesDao.isExists(dbEntity.name, dbEntity.type)
            if (isNameExisted) {
                throw Exception(CATEGORY_EXISTED)
            }
            categoriesDao.insert(dbEntity)
        }
    }

    override suspend fun updateCategory(entity: CategoryEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute { categoriesDao.update(dbEntity) }
    }

    override suspend fun deleteCategory(entity: CategoryEntity): ResultWrapper<Int> {
        val dbEntity = mapper.mapCategoryEntityToDb(entity)
        return execute {
            val transactions = transactionsDao.getAllByCategory(dbEntity.id)
            val newCategoryId = when (TransactionType.valueOf(dbEntity.type)) {
                TransactionType.Expense -> CATEGORY_EXPENSE_ID
                TransactionType.Income -> CATEGORY_INCOME_ID
            }
            val mappedTransactions = transactions.map { it.copy(categoryId = newCategoryId) }
            transactionsDao.updateAll(mappedTransactions)
            categoriesDao.delete(dbEntity)
        }
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

    override suspend fun getCategoriesFlow(type: TransactionType): ResultWrapper<Flow<List<CategoryEntity>>> {
        return execute {
            val flow = categoriesDao.getAllFlow(type.toString())
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
        return execute {
            val transactions = transactionsDao.getAllByCurrency(dbEntity.id)
            if (transactions.isNotEmpty()) {
                throw Exception(DELETE_CURRENCY_TRANSACTIONS)
            }
            val wallets = walletsDao.getAllByCurrency(dbEntity.id)
            val mappedWallets = wallets.map { it.copy(currencyId = CURRENCY_ID) }
            walletsDao.updateAll(mappedWallets)
            currenciesDao.delete(dbEntity)
        }
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
        return execute {
            val transactions = transactionsDao.getAllByWallet(dbEntity.id)
            val mappedTransactions = transactions.map { it.copy(walletId = WALLET_ID) }
            transactionsDao.updateAll(mappedTransactions)
            walletsDao.delete(dbEntity)
        }
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

    override suspend fun getComplexWallet(id: Long): ResultWrapper<ComplexWalletEntity> {
        return execute {
            val dbEntity = walletsDao.getComplexWallet(id)
            mapper.mapComplexWalletDbToEntity(dbEntity)
        }
    }
}