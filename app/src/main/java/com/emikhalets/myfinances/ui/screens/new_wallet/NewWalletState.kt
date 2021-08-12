package com.emikhalets.myfinances.ui.screens.new_wallet

data class NewWalletState(
    val name: String,
    val value: Double,
    val loading: Boolean,
    val saved: Boolean,
    val emptyNameError: Boolean,
    val valueError: Boolean,
    val error: Exception?
) {

    constructor() : this(
        name = "",
        value = 0.0,
        loading = false,
        saved = false,
        emptyNameError = false,
        valueError = false,
        error = null
    )

    fun setSavedWallet(): NewWalletState {
        return this.copy(
            loading = false,
            saved = true
        )
    }

    fun setCommonError(exception: Exception): NewWalletState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): NewWalletState {
        return this.copy(
            loading = true,
            emptyNameError = false,
            valueError = false,
            error = null
        )
    }

    fun setEmptyNameError(): NewWalletState {
        return this.copy(emptyNameError = true)
    }

    fun setValueError(): NewWalletState {
        return this.copy(valueError = true)
    }

    fun errorMessage(): String = error?.message.toString()
}