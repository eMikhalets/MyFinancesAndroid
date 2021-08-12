package com.emikhalets.myfinances.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R

enum class TransactionType(val value: Int) {
    EXPENSE(0),
    INCOME(1);

    companion object {
        private val map = values().associateBy(TransactionType::value)
        fun get(value: Int): TransactionType = map[value] ?: EXPENSE

        @Composable
        fun TransactionType.getLabel(): String {
            return when (this) {
                EXPENSE -> stringResource(R.string.new_expense)
                INCOME -> stringResource(R.string.new_income)
            }
        }
    }
}