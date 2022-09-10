package com.emikhalets.myfinances.utils.enums

enum class TransactionType {
    Expense,
    Income;

    companion object {
        fun getType(page: Int): TransactionType {
            return when (page) {
                0 -> Expense
                else -> Income
            }
        }
    }
}