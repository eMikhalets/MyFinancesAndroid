package com.emikhalets.myfinances.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R

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

        @Composable
        fun TransactionType.getName(): String {
            return when (this) {
                Expense -> stringResource(R.string.expenses)
                Income -> stringResource(R.string.incomes)
            }
        }
    }
}