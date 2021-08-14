package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.myfinances.utils.navigation.BottomNav
import com.emikhalets.myfinances.utils.navigation.navigateBack

@Composable
fun ScreenScaffold(
    navController: NavHostController,
    title: String,
    backIcon: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            AppToolbar(
                navController = navController,
                title = title,
                backIcon = backIcon
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun AppToolbar(
    navController: NavHostController,
    title: String,
    backIcon: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (backIcon) Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier.clickable { navController.navigateBack() }
            )
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = contentColorFor(backgroundColor),
        elevation = 0.dp
    )
}

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        BottomNav.Transactions,
        BottomNav.Summary,
        BottomNav.Lists
    )

    BottomNavigation {
        items.forEach { item ->
            AppBottomBarItem(
                icon = item.icon,
                label = item.label,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.AppBottomBarItem(
    icon: ImageVector,
    label: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        },
        label = { Text(stringResource(label)) },
        selected = selected,
        onClick = onClick
    )
}