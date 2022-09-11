package com.emikhalets.myfinances.presentation.screens.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.AppToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.toast

@Composable
fun WalletScreen(
    navController: NavHostController,
    walletId: Long,
    viewModel: WalletViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.getWallet(walletId) }

    LaunchedEffect(state.wallet) {
        if (state.wallet != null) {
            name = state.wallet.name
        }
    }

    LaunchedEffect(state.walletSaved) {
        if (state.walletSaved) navController.popBackStack()
    }

    LaunchedEffect(state.walletDeleted) {
        if (state.walletDeleted) navController.popBackStack()
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    WalletScreen(
        navController = navController,
        wallet = state.wallet,
        name = name,
        value = 0.0,
        onNameChange = { name = it },
        onSaveClick = { viewModel.saveWallet(name) },
        onDeleteClick = viewModel::deleteWallet
    )
}

@Composable
private fun WalletScreen(
    navController: NavHostController,
    wallet: Wallet?,
    name: String,
    value: Double,
    onNameChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    wallet ?: return
    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_wallet_screen))
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AppTextField(name, onNameChange, labelRes = R.string.label_name)
            Spacer(Modifier.height(16.dp))

            ValueText(value)
            Spacer(Modifier.height(16.dp))

            ControlButtons(onSaveClick, onDeleteClick)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ValueText(value: Double) {
    AppText(
        text = stringResource(R.string.app_money_value, value),
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun ControlButtons(onSaveClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.delete),
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f)
        )
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsPreview() {
    MyFinancesTheme {
        WalletScreen(
            navController = rememberNavController(),
            wallet = Wallet("Preview wallet name"),
            name = "Preview wallet name",
            value = 656565.65,
            onNameChange = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}