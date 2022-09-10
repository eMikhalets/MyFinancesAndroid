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

        fun TransactionType.getDefaultId(): Long {
            return when (this) {
                Expense -> -1L
                Income -> -2L
            }
        }
    }
}