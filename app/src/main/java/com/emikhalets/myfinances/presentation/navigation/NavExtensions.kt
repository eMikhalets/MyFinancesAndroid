package com.emikhalets.myfinances.presentation.navigation

import androidx.navigation.NavHostController
import com.emikhalets.myfinances.utils.enums.TransactionType

const val ARG_TRANSACTION_ID = "transaction_id"
const val ARG_TRANSACTION_TYPE = "transaction_type"

const val ROUTE_EDIT_TRANSACTION = "transaction_edit"

fun NavHostController.navToTransactionEdit(id: Long?, type: TransactionType) {
    this.navigate("$ROUTE_EDIT_TRANSACTION/{$ARG_TRANSACTION_ID}/{$ARG_TRANSACTION_TYPE}")
}