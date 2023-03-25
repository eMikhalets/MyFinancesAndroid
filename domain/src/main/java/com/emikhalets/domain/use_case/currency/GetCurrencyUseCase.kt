package com.emikhalets.domain.use_case.currency

import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(id: Long) = repository.getCurrencyFlow(id)
}