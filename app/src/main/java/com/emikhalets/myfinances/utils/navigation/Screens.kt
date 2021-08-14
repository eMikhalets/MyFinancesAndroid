package com.emikhalets.myfinances.utils.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.myfinances.R

object Screens {
    const val Transactions = "transactions"
    const val Summary = "summary"
    const val Lists = "lists"
    const val NewTransaction = "new_transaction"
    const val NewCategory = "new_category"
    const val NewWallet = "new_wallet"
    const val Categories = "categories"
}

sealed class BottomNav(val route: String, val icon: ImageVector, @StringRes val label: Int) {
    object Transactions : BottomNav(
        Screens.Transactions,
        Icons.Default.Circle,
        R.string.title_transactions
    )

    object Summary : BottomNav(
        Screens.Summary,
        Icons.Default.CropSquare,
        R.string.title_summary
    )

    object Lists : BottomNav(
        Screens.Lists,
        Icons.Default.ListAlt,
        R.string.title_lists
    )
}