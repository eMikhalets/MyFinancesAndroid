package com.emikhalets.myfinances.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R

enum class TransactionType(val value: Int) {
    Expense(0),
    Income(1),
    None(2);

    companion object {
        private val map = values().associateBy(TransactionType::value)
        fun get(value: Int): TransactionType = map[value] ?: None

        @Composable
        fun TransactionType.getLabel(): String {
            return when (this) {
                Expense -> stringResource(R.string.new_expense)
                Income -> stringResource(R.string.new_income)
                None -> "none"
            }
        }
    }
}