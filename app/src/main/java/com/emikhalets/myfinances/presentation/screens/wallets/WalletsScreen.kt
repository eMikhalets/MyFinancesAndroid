package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.navigateToTransaction
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.toast

@Composable
fun WalletsScreen(
    navController: NavHostController,
    viewModel: WalletsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.getWallets() }

    LaunchedEffect(state.error) { toast(context, state.error) }

    WalletsScreen(
        navController = navController,
        wallets = state.wallets
    )
}

@Composable
private fun WalletsScreen(navController: NavHostController, wallets: List<Wallet>) {
    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_wallets_screen))
    }) {
        Column(Modifier.fillMaxWidth()) {
            WalletsList(navController, wallets)
            AddButton()
        }
    }
}

@Composable
private fun WalletsList(navController: NavHostController, wallets: List<Wallet>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(wallets) { wallet ->
            WalletItem(navController, wallet)
        }
    }
}

@Composable
private fun WalletItem(navController: NavHostController, wallet: Wallet) {
    Column(Modifier.fillMaxWidth()) {
        AppText(
            text = wallet.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigateToTransaction(wallet.id) }
        )
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
private fun AddButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .clickable { /* TODO: add dialog new wallet */ }
            .padding(16.dp)
    ) {
        AppText(
            text = stringResource(R.string.add_transaction),
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        WalletsScreen(
            navController = rememberNavController(),
            wallets = PreviewEntities.getWalletsScreenList()
        )
    }
}