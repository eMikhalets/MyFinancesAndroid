package com.emikhalets.domain.use_case.category

import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(id: Long) = repository.getCategoryFlow(id)
}