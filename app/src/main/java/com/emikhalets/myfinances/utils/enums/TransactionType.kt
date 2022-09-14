package com.emikhalets.myfinances.utils.enums

enum class TransactionType {
    Expense,
    Income;

    companion object {

        fun TransactionType.getDefaultId(): Long {
            return when (this) {
                Expense -> -1L
                Income -> -2L
            }
        }
    }
}