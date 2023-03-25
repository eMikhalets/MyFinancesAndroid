package com.emikhalets.domain.use_case.transaction

import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(entity: TransactionEntity) = repository.updateTransaction(entity)
}