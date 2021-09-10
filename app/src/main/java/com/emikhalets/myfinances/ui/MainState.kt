package com.emikhalets.myfinances.ui

data class MainState(
    val defaultWalletCreated: Boolean = false,
    val error: Exception? = null
) {

    fun errorMessage(): String {
        return error?.message ?: ""
    }
}