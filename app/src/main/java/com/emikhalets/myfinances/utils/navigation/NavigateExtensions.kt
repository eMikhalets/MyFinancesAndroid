package com.emikhalets.myfinances.utils.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.emikhalets.myfinances.utils.enums.TransactionType

object NavArgs {
    const val TRANSACTION_TYPE = "arg_transaction_type"
    const val TRANSACTION_ID = "arg_transaction_id"
}

fun NavHostController.navigateAsStart(route: String) {
    if (currentDestination?.route != route) {
        navigate(route) {
            popUpTo(graph.findStartDestination().id) { saveState = true }
            restoreState = true
        }
    }
}

fun NavHostController.navigateToNewTransaction(type: TransactionType) {
    navigate(Screens.NewTransaction, bundleOf(NavArgs.TRANSACTION_TYPE to type))
}

fun NavHostController.navigateToTransactionDetails(id: Long) {
    navigate(Screens.TransactionDetails, bundleOf(NavArgs.TRANSACTION_ID to id))
}

private fun NavController.navigate(
    route: String,
    params: Bundle?,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.currentBackStackEntry?.arguments = params
    navigate(route, builder)
}

//======================================================================================

@Composable
fun <T> NavController.getSerializable(
    key: String,
    content: @Composable (T) -> Unit
) {
    this.previousBackStackEntry?.arguments?.getSerializable(key)?.let {
        content(it as T)
    }
}

@Composable
fun NavController.getLong(
    key: String,
    content: @Composable (Long) -> Unit
) {
    this.previousBackStackEntry?.arguments?.getLong(key)?.let {
        content(it)
    }
}