package com.emikhalets.myfinances.utils.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.emikhalets.myfinances.R

enum class Screen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int?,
    val isBottomNavigation: Boolean
) {
    Transactions(
        route = "transactions",
        title = R.string.title_transaction,
        icon = R.drawable.ic_home,
        isBottomNavigation = true
    ),
    Summary(
        route = "summary",
        title = R.string.title_summary,
        icon = R.drawable.ic_pie_chart,
        isBottomNavigation = true
    ),
    Lists(
        route = "lists",
        title = R.string.title_lists,
        icon = R.drawable.ic_price_list,
        isBottomNavigation = true
    ),
    NewTransaction(
        route = "new_transaction",
        title = R.string.title_new_transaction,
        icon = null,
        isBottomNavigation = false
    ),
    TransactionDetails(
        route = "transaction_details",
        title = R.string.title_transaction,
        icon = null,
        isBottomNavigation = false
    );

    companion object {
        fun getBottomNavigationScreens(): List<Screen> {
            return values().toList().filter { it.isBottomNavigation }
        }
    }
}