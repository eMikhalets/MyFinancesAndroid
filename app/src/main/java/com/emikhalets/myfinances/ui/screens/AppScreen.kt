package com.emikhalets.myfinances.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.base.AppBottomBar
import com.emikhalets.myfinances.ui.base.AppNavGraph
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AppScreen(
    navController: NavHostController

) {
    MyFinancesTheme {
        Scaffold(
            topBar = {},
            bottomBar = {
                AppBottomBar(
                    navController = navController
                )
            },
            backgroundColor = MaterialTheme.colors.surface
        ) {
            AppNavGraph(navController = navController)
        }
    }
}