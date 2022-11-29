package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.navigation.AppScreen
import com.emikhalets.myfinances.presentation.navigation.AppScreen.Companion.isRootScreen

@Composable
fun AppScaffold(
    navController: NavHostController,
    title: String = "",
    content: @Composable () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.getScreen(navBackStackEntry)
    val isRootScreen = currentScreen.isRootScreen()
    val toolbarTitle = title.ifEmpty {
        stringResource(currentScreen?.title ?: R.string.app_name_title)
    }

    Scaffold(
        topBar = { AppToolbar(navController, toolbarTitle, isRootScreen) },
        content = { Box(Modifier.padding(it)) { content() } }
    )
}

@Composable
private fun AppToolbar(navController: NavHostController, title: String, isRootScreen: Boolean) {
    TopAppBar(
        title = { Text(text = title) },
        elevation = 0.dp,
        navigationIcon = {
            if (!isRootScreen) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(20.dp, 16.dp)
                )
            }
        }
    )
}