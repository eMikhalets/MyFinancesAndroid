package com.emikhalets.myfinances.ui.screens.new_wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.navigateFromStartToNewWallet
import com.emikhalets.myfinances.utils.navigateFromStartToTransactions

//@Composable
//fun FirstLaunchScreen(
//    navController: NavHostController,
//    viewModel: FirstLaunchVM = hiltViewModel()
//) {
//    val state = viewModel.state
//    LaunchedEffect("123") {
//        viewModel.getWallets()
//    }
//
//    when {
//        state.wallets == null -> {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.logo),
//                    contentDescription = ""
//                )
//            }
//        }
//        state.wallets.isEmpty() -> navController.navigateFromStartToNewWallet()
//        state.wallets.isNotEmpty() -> navController.navigateFromStartToTransactions()
//    }
//}