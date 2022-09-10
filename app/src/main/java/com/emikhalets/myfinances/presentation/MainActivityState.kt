package com.emikhalets.myfinances.presentation

data class MainActivityState(
    val defaultWalletCreated: Boolean = false,
    val error: String = "",
) {

    fun setWalletCreated(): MainActivityState {
        return this.copy(defaultWalletCreated = true, error = "")
    }

    fun setError(message: String): MainActivityState {
        return this.copy(error = message)
    }
}