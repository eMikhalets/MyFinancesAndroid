package com.emikhalets.myfinances.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.presentation.screens.main.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.MAIN.route) {
        composable(AppScreen.MAIN.route) {
            MainScreen(navController)
        }
    }
}