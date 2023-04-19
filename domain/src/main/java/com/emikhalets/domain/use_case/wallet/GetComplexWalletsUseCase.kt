package com.emikhalets.domain.use_case.wallet

import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetComplexWalletsUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke() = repository.getComplexWallets()
}