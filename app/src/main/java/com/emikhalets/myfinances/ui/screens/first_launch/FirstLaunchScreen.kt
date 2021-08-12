package com.emikhalets.myfinances.ui.screens.first_launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.navigateToNewWalletAsStart
import com.emikhalets.myfinances.utils.navigateToTransactionsAsStart

@Composable
fun FirstLaunchScreen(
    navController: NavHostController,
    viewModel: FirstLaunchVM = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect("init_launch") {
        viewModel.getWallets()
    }
    LaunchedEffect(state) {
        if (state.wallets != null) {
            if (state.wallets.isEmpty()) navController.navigateToNewWalletAsStart()
            if (state.wallets.isNotEmpty()) navController.navigateToTransactionsAsStart()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7F7F7))
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = ""
        )
    }
}