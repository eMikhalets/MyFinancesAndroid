package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.utils.enums.MyIcons
import com.emikhalets.myfinances.utils.navigation.BottomNav
import com.emikhalets.myfinances.utils.navigation.navigateAsStart

@Composable
fun ScreenScaffold(
    navController: NavHostController,
    title: String,
    icon: Int = MyIcons.App.icon,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { AppToolbar(navController = navController, title = title, icon = icon) },
        content = { Box(Modifier.padding(it)) { content() } }
    )
}

@Composable
fun AppToolbar(
    navController: NavHostController,
    title: String,
    icon: Int
) {
    TopAppBar(
        title = {
            AppText(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            AppIcon(
                icon = icon,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        },
        actions = {},
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
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
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = ""
                    )
                },
                label = { Text(stringResource(item.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navController.navigateAsStart(item.route) }
            )
        }
    }
}