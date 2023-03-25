package com.emikhalets.domain.use_case.transaction

import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(id: Long) = repository.getTransactionFlow(id)
}