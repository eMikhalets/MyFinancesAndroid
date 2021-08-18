package com.emikhalets.myfinances.utils.navigation

import androidx.annotation.StringRes
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.enums.AppIcon

object Screens {
    const val Transactions = "transactions"
    const val Summary = "summary"
    const val Lists = "lists"
    const val NewTransaction = "new_transaction"
}

sealed class BottomNav(val route: String, val icon: Int, @StringRes val label: Int) {
    object Transactions : BottomNav(
        Screens.Transactions,
        AppIcon.Home.icon,
        R.string.title_transactions
    )

    object Summary : BottomNav(
        Screens.Summary,
        AppIcon.PieChart.icon,
        R.string.title_summary
    )

    object Lists : BottomNav(
        Screens.Lists,
        AppIcon.PriceList.icon,
        R.string.title_lists
    )
}