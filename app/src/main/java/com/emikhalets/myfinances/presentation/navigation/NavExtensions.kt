package com.emikhalets.myfinances.presentation.navigation

import androidx.navigation.NavHostController
import com.emikhalets.myfinances.utils.enums.TransactionType

const val ARG_TRANSACTION_ID = "transaction_id"
const val ARG_TRANSACTION_TYPE = "transaction_type"

fun NavHostController.navToTransactionEdit(id: Long?, type: TransactionType) {
    this.navigate("transaction_edit/$id/$type")
}