package com.emikhalets.domain.use_case.category

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(entity: CategoryEntity) = repository.deleteCategory(entity)
}