package com.emikhalets.myfinances.utils.navigation

import androidx.annotation.StringRes
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.enums.MyIcons

object Screens {
    const val Transactions = "transactions"
    const val Summary = "summary"
    const val Lists = "lists"
    const val NewTransaction = "new_transaction"
    const val TransactionDetails = "transaction_details"
}

sealed class BottomNav(val route: String, val icon: Int, @StringRes val label: Int) {
    object Transactions : BottomNav(
        Screens.Transactions,
        MyIcons.Home.icon,
        R.string.title_transactions
    )

    object Summary : BottomNav(
        Screens.Summary,
        MyIcons.PieChart.icon,
        R.string.title_summary
    )

    object Lists : BottomNav(
        Screens.Lists,
        MyIcons.PriceList.icon,
        R.string.title_lists
    )
}