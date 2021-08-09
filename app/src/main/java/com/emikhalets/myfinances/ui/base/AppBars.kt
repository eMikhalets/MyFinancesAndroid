package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
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
import com.emikhalets.myfinances.utils.Screens
import com.emikhalets.myfinances.utils.navigateToSummary
import com.emikhalets.myfinances.utils.navigateToTransactions

@Composable
fun AppScaffold(
    navController: NavHostController,
    showTopBar: Boolean,
    showBottomBar: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {},
        bottomBar = {
            if (showBottomBar) AppBottomBar(navController = navController)
        },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

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