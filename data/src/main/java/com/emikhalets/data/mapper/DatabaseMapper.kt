package com.emikhalets.data.mapper

import com.emikhalets.data.database.table.CategoryDb
import com.emikhalets.data.database.table.CurrencyDb
import com.emikhalets.data.database.table.TransactionDb
import com.emikhalets.data.database.table.WalletDb
import com.emikhalets.data.database.table.embedded.ComplexWalletDb
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import javax.inject.Inject

class DatabaseMapper @Inject constructor() {

    fun mapCategoryEntityToDb(entity: CategoryEntity): CategoryDb {
        return CategoryDb(
            id = entity.id,
            name = entity.name,
            type = entity.type.toString(),
        )
    }

    fun mapCategoryDbToEntity(dbEntity: CategoryDb): CategoryEntity {
        return CategoryEntity(
            id = dbEntity.id,
            name = dbEntity.name,
            type = TransactionType.valueOf(dbEntity.type),
        )
    }

    fun mapCategoriesListDbToEntity(dbList: List<CategoryDb>): List<CategoryEntity> {
        return dbList.map { dbEntity ->
            CategoryEntity(
                id = dbEntity.id,
                name = dbEntity.name,
                type = TransactionType.valueOf(dbEntity.type),
            )
        }
    }

    fun mapTransactionEntityToDb(entity: TransactionEntity): TransactionDb {
        return TransactionDb(
            id = entity.id,
            categoryId = entity.categoryId,
            walletId = entity.walletId,
            currencyId = entity.currencyId,
            value = entity.value,
            type = entity.type.toString(),
            note = entity.note,
            timestamp = entity.timestamp
        )
    }

    fun mapTransactionDbToEntity(dbEntity: TransactionDb): TransactionEntity {
        return TransactionEntity(
            id = dbEntity.id,
            categoryId = dbEntity.categoryId,
            walletId = dbEntity.walletId,
            currencyId = dbEntity.currencyId,
            value = dbEntity.value,
            type = TransactionType.valueOf(dbEntity.type),
            note = dbEntity.note,
            timestamp = dbEntity.timestamp
        )
    }

    fun mapTransactionsListDbToEntity(dbList: List<TransactionDb>): List<TransactionEntity> {
        return dbList.map { dbEntity ->
            TransactionEntity(
                id = dbEntity.id,
                categoryId = dbEntity.categoryId,
                walletId = dbEntity.walletId,
                currencyId = dbEntity.currencyId,
                value = dbEntity.value,
                type = TransactionType.valueOf(dbEntity.type),
                note = dbEntity.note,
                timestamp = dbEntity.timestamp
            )
        }
    }

    fun mapCurrencyEntityToDb(entity: CurrencyEntity): CurrencyDb {
        return CurrencyDb(
            id = entity.id,
            name = entity.name,
            symbol = entity.symbol
        )
    }

    fun mapCurrencyDbToEntity(dbEntity: CurrencyDb): CurrencyEntity {
        return CurrencyEntity(
            id = dbEntity.id,
            name = dbEntity.name,
            symbol = dbEntity.symbol
        )
    }

    fun mapCurrenciesListDbToEntity(dbList: List<CurrencyDb>): List<CurrencyEntity> {
        return dbList.map { dbEntity ->
            CurrencyEntity(
                id = dbEntity.id,
                name = dbEntity.name,
                symbol = dbEntity.symbol
            )
        }
    }

    fun mapWalletEntityToDb(entity: WalletEntity): WalletDb {
        return WalletDb(
            id = entity.id,
            name = entity.name,
            currencyId = entity.currencyId,
            initialValue = entity.initialValue
        )
    }

    fun mapWalletDbToEntity(dbEntity: WalletDb): WalletEntity {
        return WalletEntity(
            id = dbEntity.id,
            name = dbEntity.name,
            currencyId = dbEntity.currencyId,
            initialValue = dbEntity.initialValue
        )
    }

    fun mapWalletsListDbToEntity(dbList: List<WalletDb>): List<WalletEntity> {
        return dbList.map { dbEntity ->
            WalletEntity(
                id = dbEntity.id,
                name = dbEntity.name,
                currencyId = dbEntity.currencyId,
                initialValue = dbEntity.initialValue
            )
        }
    }

    fun mapComplexWalletDbToEntity(dbEntity: ComplexWalletDb): ComplexWalletEntity {
        return ComplexWalletEntity(
            wallet = mapWalletDbToEntity(dbEntity.wallet),
            transactions = mapTransactionsListDbToEntity(dbEntity.transactions)
        )
    }
}