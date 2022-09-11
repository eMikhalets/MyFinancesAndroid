package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var walletDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.getWallets() }

    LaunchedEffect(state.wallet) { state.wallet?.let { walletDialogVisible = true } }

    LaunchedEffect(state.error) { toast(context, state.error) }

    WalletsScreen(
        navController = navController,
        wallets = state.wallets,
        onWalletClick = viewModel::getWallet,
        onAddClick = { walletDialogVisible = true }
    )

    if (walletDialogVisible) {
        WalletDialog(
            wallet = state.wallet,
            onDismiss = { walletDialogVisible = false },
            onSaveClick = viewModel::saveWallet,
            onDeleteClick = viewModel::deleteWallet
        )
    }
}

@Composable
private fun WalletsScreen(
    navController: NavHostController,
    wallets: List<Wallet>,
    onWalletClick: (Long) -> Unit,
    onAddClick: () -> Unit,
) {
    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_wallets_screen))
    }) {
        Column(Modifier.fillMaxWidth()) {
            WalletsList(wallets, onWalletClick)
            AddButton(onAddClick)
        }
    }
}

@Composable
private fun ColumnScope.WalletsList(wallets: List<Wallet>, onWalletClick: (Long) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
    ) {
        items(wallets) { wallet ->
            WalletItem(wallet, onWalletClick)
        }
    }
}

@Composable
private fun WalletItem(wallet: Wallet, onWalletClick: (Long) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        AppText(
            text = wallet.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onWalletClick(wallet.id) }
        )
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
private fun AddButton(onAddClick: () -> Unit) {
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
            wallets = PreviewEntities.getWalletsScreenList(),
            onWalletClick = {},
            onAddClick = {}
        )
    }
}