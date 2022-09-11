package com.emikhalets.myfinances.presentation

data class MainActivityState(
    val createdWalletId: Long = 0,
    val error: String = "",
) {

    fun setWalletCreated(id: Long): MainActivityState {
        return this.copy(createdWalletId = id)
    }

    fun setError(message: String): MainActivityState {
        return this.copy(error = message)
    }
}