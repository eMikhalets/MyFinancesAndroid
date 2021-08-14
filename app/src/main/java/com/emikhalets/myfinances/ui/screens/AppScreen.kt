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
import com.emikhalets.myfinances.utils.navigation.AppNavGraph

@Composable
fun AppScreen(navController: NavHostController) {

    MyFinancesTheme {
        Scaffold(
            bottomBar = { AppBottomBar(navController = navController) },
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavGraph(navController = navController)
            }
        }
    }
}