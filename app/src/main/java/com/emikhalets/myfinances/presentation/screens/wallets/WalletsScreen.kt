//package com.emikhalets.myfinances.presentation.screens.wallets
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Divider
//import androidx.compose.material.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.emikhalets.myfinances.R
//import com.emikhalets.myfinances.data.entity.Wallet
//import com.emikhalets.myfinances.data.entity.WalletEntity
//import com.emikhalets.myfinances.presentation.core.AppToolbar
//import com.emikhalets.myfinances.presentation.core.ScreenScaffold
//import com.emikhalets.myfinances.presentation.core.TextPrimary
//import com.emikhalets.myfinances.presentation.theme.AppTheme
//import com.emikhalets.myfinances.presentation.theme.boxBackground
//import com.emikhalets.myfinances.presentation.theme.textSecondary
//import com.emikhalets.myfinances.utils.PreviewEntities
//import com.emikhalets.myfinances.utils.toast
//
//@Composable
//fun WalletsScreen(
//    navController: NavHostController,
//    viewModel: WalletsViewModel = hiltViewModel(),
//) {
//    val state = viewModel.state
//    val context = LocalContext.current
//
//    var walletDialogVisible by remember { mutableStateOf(false) }
//    var wallet by remember { mutableStateOf<Wallet?>(null) }
//
//    LaunchedEffect(Unit) { viewModel.getWallets() }
//
//    LaunchedEffect(state.wallets) {
//        if (wallet != null) {
//            wallet = state.wallets.find { it.wallet.id == wallet?.id }?.wallet
//        }
//    }
//
//    LaunchedEffect(state.error) { toast(context, state.error) }
//
//    WalletsScreen(
//        navController = navController,
//        wallets = state.wallets,
//        currentId = viewModel.prefs.currentWalletId,
//        onWalletClick = {
//            wallet = it
//            walletDialogVisible = true
//        },
//        onAddClick = {
//            wallet = null
//            walletDialogVisible = true
//        }
//    )
//
//    if (walletDialogVisible) {
//        WalletDialog(
//            wallet = wallet,
//            onDismiss = { walletDialogVisible = false },
//            onSaveClick = viewModel::saveWallet,
//            onDeleteClick = viewModel::deleteWallet
//        )
//    }
//}
//
//@Composable
//private fun WalletsScreen(
//    navController: NavHostController,
//    wallets: List<WalletEntity>,
//    currentId: Long,
//    onWalletClick: (Wallet) -> Unit,
//    onAddClick: () -> Unit,
//) {
//    ScreenScaffold({
//        AppToolbar(navController, stringResource(R.string.title_wallets_screen))
//    }) {
//        Column(Modifier.fillMaxWidth()) {
//            WalletsList(wallets, currentId, onWalletClick)
//            AddButton(onAddClick)
//        }
//    }
//}
//
//@Composable
//private fun ColumnScope.WalletsList(
//    wallets: List<WalletEntity>,
//    currentId: Long,
//    onWalletClick: (Wallet) -> Unit,
//) {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .weight(1f)
//    ) {
//        items(wallets) { entity ->
//            WalletItem(entity, currentId, onWalletClick)
//        }
//    }
//}
//
//@Composable
//private fun WalletItem(entity: WalletEntity, currentId: Long, onWalletClick: (Wallet) -> Unit) {
//    val fontWeight = if (entity.wallet.id != currentId) {
//        FontWeight.Normal
//    } else {
//        FontWeight.Bold
//    }
//
//    Column(Modifier.fillMaxWidth()) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onWalletClick(entity.wallet) }
//        ) {
//            TextPrimary(
//                text = entity.wallet.name,
//                size = 18.sp,
//                fontWeight = fontWeight,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .padding(8.dp)
//            )
//            TextPrimary(
//                text = entity.value.toString(),
//                size = 20.sp,
//                fontWeight = fontWeight,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//        Divider(color = MaterialTheme.colors.textSecondary)
//    }
//}
//
//@Composable
//private fun AddButton(onAddClick: () -> Unit) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colors.boxBackground)
//            .clickable { onAddClick() }
//            .padding(16.dp)
//    ) {
//        TextPrimary(
//            text = stringResource(R.string.add_transaction),
//            size = 20.sp
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    AppTheme {
//        WalletsScreen(
//            navController = rememberNavController(),
//            wallets = PreviewEntities.getWalletsScreenList(),
//            currentId = 2,
//            onWalletClick = {},
//            onAddClick = {}
//        )
//    }
//}