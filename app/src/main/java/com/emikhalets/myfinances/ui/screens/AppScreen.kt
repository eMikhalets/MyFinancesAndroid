package com.emikhalets.myfinances.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AppNavGraph

@Composable
fun AppScreen(
    navController: NavHostController

) {
    MyFinancesTheme {
        AppNavGraph(navController = navController)
    }
}