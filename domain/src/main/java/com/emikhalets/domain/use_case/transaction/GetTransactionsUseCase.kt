package com.emikhalets.domain.use_case.transaction

import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(walletId: Long) = repository.getTransactionsFlow(walletId)

    suspend operator fun invoke(type: TransactionType, walletId: Long) =
        repository.getTransactionsFlow(type, walletId)

    suspend operator fun invoke(start: Long, end: Long) =
        repository.getTransactionsFlow(start, end)
}