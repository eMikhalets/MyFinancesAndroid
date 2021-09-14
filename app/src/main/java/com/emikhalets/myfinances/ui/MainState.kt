package com.emikhalets.myfinances.ui

data class MainState(
    val defaultWalletCreated: Boolean = false,
    val error: Exception? = null
) {

    fun setWalletCreated(): MainState {
        return this.copy(
            defaultWalletCreated = true,
            error = null
        )
    }

    fun setError(exception: Exception): MainState {
        return this.copy(error = exception)
    }
}