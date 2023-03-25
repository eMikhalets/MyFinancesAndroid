package com.emikhalets.domain.use_case.wallet

import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(id: Long) = repository.getWalletFlow(id)
}