package com.emikhalets.domain

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import kotlin.random.Random

object PreviewEntity {

    private fun getType() = when (Random.nextBoolean()) {
        true -> TransactionType.Expense
        false -> TransactionType.Income
    }

    private fun getNote() = when (Random.nextBoolean()) {
        true -> "Test note"
        false -> ""
    }

    fun getCurrency(id: Long = 0) = CurrencyEntity(
        id = id,
        name = "Test currency #$id",
        symbol = "$"
    )

    fun getCategory(id: Long = 0, type: TransactionType = getType()) = CategoryEntity(
        id = id,
        name = "Test category #$id",
        type = type,
    )

    fun getWallet(id: Long = 0) = WalletEntity(
        id = id,
        currencyId = 0,
        name = "Test wallet #$id",
        initialValue = Random.nextDouble(0.0, 1_000_000.00)
    )

    fun getTransaction(id: Long = 0, type: TransactionType = getType()) = TransactionEntity(
        id = id,
        categoryId = 0,
        walletId = 0,
        currencyId = 0,
        value = Random.nextDouble(0.0, 1_000_000.00),
        type = type,
        note = getNote()
    )

    fun getTransactionsList(size: Int) = List(size) { getTransaction(it.toLong()) }

    fun getComplexTransaction(id: Long = 0, type: TransactionType = getType()) =
        ComplexTransactionEntity(
            transaction = getTransaction(id, type),
            category = getCategory(Random.nextLong(1, 10), type),
            wallet = getWallet(Random.nextLong(1, 10)),
            currency = getCurrency(Random.nextLong(1, 10))
        )

    fun getComplexWallet(id: Long = 0, type: TransactionType = getType()) =
        ComplexWalletEntity(
            transactions = getTransactionsList(20),
            wallet = getWallet(Random.nextLong(1, 10)),
            currency = getCurrency(Random.nextLong(1, 10))
        )

    fun getComplexTransactionsList(size: Int) = List(size) { getComplexTransaction(it.toLong()) }
}