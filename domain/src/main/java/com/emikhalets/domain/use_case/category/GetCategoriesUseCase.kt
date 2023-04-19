package com.emikhalets.domain.use_case.category

import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke() = repository.getCategoriesFlow()

    suspend operator fun invoke(type: TransactionType) = repository.getCategoriesFlow(type)
}