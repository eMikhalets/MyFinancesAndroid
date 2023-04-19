package com.emikhalets.presentation.screens.wallets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.presentation.core.AppButton
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.textSecondary

@Composable
fun WalletsScreen(
    onsWalletClick: (id: Long) -> Unit,
    onAddWalletClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: WalletsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getWallets()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(title = stringResource(Screen.Wallets.title), onBackClick = onBackClick)
        ScreenContent(
            wallets = uiState.wallets,
            onsWalletClick = { id -> onsWalletClick(id) },
            onAddWalletClick = { onAddWalletClick() }
        )
    }

    val errorMessage = error
    if (errorMessage != null) {
        MessageDialog(
            message = errorMessage.asString(),
            onDismiss = { error = null }
        )
    }
}

@Composable
private fun ScreenContent(
    wallets: List<WalletEntity>,
    onsWalletClick: (Long) -> Unit,
    onAddWalletClick: () -> Unit,
) {
    WalletsList(
        wallets = wallets,
        onsWalletClick = { id -> onsWalletClick(id) },
        onAddWalletClick = onAddWalletClick,
    )
}

@Composable
private fun WalletsList(
    wallets: List<WalletEntity>,
    onsWalletClick: (Long) -> Unit,
    onAddWalletClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(wallets) { wallet ->
                WalletItem(
                    wallet = wallet,
                    onsWalletClick = onsWalletClick
                )
            }
        }
        Divider()
        AppButton(
            text = "stringResource(R.string.add)",
            onClick = onAddWalletClick
        )
    }
}

@Composable
private fun WalletItem(wallet: WalletEntity, onsWalletClick: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onsWalletClick(wallet.id) }
    ) {
        Text(
            text = wallet.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Divider(color = MaterialTheme.colors.textSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            wallets = listOf(
                WalletEntity(0, "Name 1", 0, 0.0),
                WalletEntity(0, "Name 1", 0, 0.0),
                WalletEntity(0, "Name 1", 0, 0.0),
                WalletEntity(0, "Name 1", 0, 0.0),
                WalletEntity(0, "Name 1", 0, 0.0),
                WalletEntity(0, "Name 1", 0, 0.0),
            ),
            onsWalletClick = {},
            onAddWalletClick = {}
        )
    }
}