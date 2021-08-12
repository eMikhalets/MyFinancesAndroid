package com.emikhalets.myfinances.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.base.AppBottomBar
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AppNavGraph
import com.emikhalets.myfinances.utils.Screens

@Composable
fun AppScreen(
    navController: NavHostController

) {
    MyFinancesTheme {
        Scaffold(
            topBar = {},
            bottomBar = {
                if (isShowBottomBar(navController)) {
                    AppBottomBar(navController = navController)
                }
            },
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavGraph(navController = navController)
            }
        }
    }
}

private fun isShowBottomBar(navController: NavHostController): Boolean {
    return navController.currentDestination?.route == Screens.FirstLaunch
            || navController.currentDestination?.route == Screens.NewWallet
}