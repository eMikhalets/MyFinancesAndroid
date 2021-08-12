package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.Screens
import com.emikhalets.myfinances.utils.navigateToSummary
import com.emikhalets.myfinances.utils.navigateToTransactions

@Composable
fun AppBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentScreen = navController.currentDestination?.route


    val height = if (currentScreen == Screens.FirstLaunch || currentScreen == Screens.NewWallet) {
        56.dp
    } else {
        0.dp
    }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        modifier = modifier.height(height)
    ) {
        AppBottomBarItem(
            selected = currentScreen == Screens.Transactions,
            icon = Icons.Default.Circle,
            label = stringResource(R.string.title_transactions),
            onClick = { navController.navigateToTransactions() }
        )
        AppBottomBarItem(
            selected = currentScreen == Screens.Summary,
            icon = Icons.Default.ListAlt,
            label = stringResource(R.string.title_summary),
            onClick = { navController.navigateToSummary() }
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