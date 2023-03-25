package com.emikhalets.domain.use_case.wallet

import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class UpdateWalletUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(entity: WalletEntity) = repository.updateWallet(entity)
}