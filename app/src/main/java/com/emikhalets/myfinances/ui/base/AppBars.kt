package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.enums.MyIcons
import com.emikhalets.myfinances.utils.navigation.Screen
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
            when (icon) {
                R.drawable.ic_launcher_foreground -> {
                    AppImage(icon)
                }
                else -> {
                    AppIcon(
                        drawable = icon,
                        size = 0.dp,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
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
    items: List<Screen>
) {
    BottomNavigation {
        items.forEach { screen ->
            val isSelected: Boolean = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } ?: false

            val color = if (isSelected) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            }

            BottomNavigationItem(
                icon = {
                    AppIcon(
                        drawable = screen.icon,
                        size = 20.dp,
                        color = color
                    )
                },
                label = {
                    AppText(
                        text = stringResource(screen.title),
                        fontColor = color,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) {
                            FontWeight.Medium
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = isSelected,
                onClick = { navController.navigateAsStart(screen.route) }
            )
        }
    }
}