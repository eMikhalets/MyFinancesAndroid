package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R

@Composable
fun AppBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(Screens.TRANSACTIONS) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        modifier = modifier
    ) {
        AppBottomBarItem(
            selected = currentScreen == Screens.TRANSACTIONS,
            icon = Icons.Default.Circle,
            label = stringResource(R.string.title_transactions),
            onClick = {
                currentScreen = Screens.TRANSACTIONS
                navController.navigateToTransactions()
            }
        )
        AppBottomBarItem(
            selected = currentScreen == Screens.SUMMARY,
            icon = Icons.Default.ListAlt,
            label = stringResource(R.string.title_summary),
            onClick = {
                currentScreen = Screens.SUMMARY
                navController.navigateToSummary()
            }
        )
    }
}

@Composable
private fun RowScope.AppBottomBarItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
//    val iconColor = if (selected) MaterialTheme.colors.onPrimary
//    else MaterialTheme.colors.error
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = ""
            )
        },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}