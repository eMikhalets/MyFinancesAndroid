package com.emikhalets.myfinances.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppBottomBar
import com.emikhalets.myfinances.ui.base.AppToolbar
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AppNavGraph

@Composable
fun AppScreen(
    navController: NavHostController,
    sharedViewModel: SharedVM = hiltViewModel()
) {
    val state = sharedViewModel.state
    var showToolbar by remember { mutableStateOf(false) }
    var showBottomNav by remember { mutableStateOf(false) }
    var selectedWallet by remember { mutableStateOf(Wallet()) }

    LaunchedEffect("innit_launch") {
        sharedViewModel.getWallets()
    }
    LaunchedEffect(state) {
        if (state.wallets.isNotEmpty()) selectedWallet = state.wallets.first()
    }

    MyFinancesTheme {
        Scaffold(
            topBar = {
                if (showToolbar) {
                    AppToolbar(
                        navController = navController,
                        wallet = selectedWallet,
                        selectedWallet = { selectedWallet = it }
                    )
                }
            },
            bottomBar = {
                if (showBottomNav) {
                    AppBottomBar(navController = navController)
                }
            },
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavGraph(
                    navController = navController,
                    selectedWallet = selectedWallet,
                    showToolbar = { bool -> showToolbar = bool },
                    showBottomNav = { bool -> showBottomNav = bool }
                )
            }
        }
    }
}