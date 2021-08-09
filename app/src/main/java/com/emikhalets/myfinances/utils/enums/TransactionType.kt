package com.emikhalets.myfinances.utils.enums

enum class TransactionType(val value: Int) {
    EXPENSE(0),
    INCOME(1);

    companion object {
        private val map = values().associateBy(TransactionType::value)
        fun get(value: Int): TransactionType = map[value] ?: EXPENSE
    }
}