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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.utils.navigation.BottomNav
import com.emikhalets.myfinances.utils.navigation.navigateAsStart
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
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
        },
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
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<BottomNav>
) {
    BottomNavigation {
        items.forEach { item ->
            AppBottomBarItem(
                icon = item.icon,
                label = item.label,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navController.navigateAsStart(item.route) }
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