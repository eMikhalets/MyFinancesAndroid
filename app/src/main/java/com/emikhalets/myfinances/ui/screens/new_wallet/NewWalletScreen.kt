package com.emikhalets.myfinances.ui.screens.new_wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.utils.navigateToTransactionsAsStart

@Composable
fun NewWalletScreen(
    navController: NavHostController,
    viewModel: NewWalletVM = hiltViewModel()
) {
    val state = viewModel.state
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state.saved) navController.navigateToTransactionsAsStart()
    }

    Column {
        AppTextField(
            label = stringResource(R.string.note),
            value = name,
            onValueChange = { name = it },
            keyboardType = KeyboardType.Text,
            emptyError = state.emptyNameError,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        AppTextField(
            label = stringResource(R.string.start_value),
            value = value,
            onValueChange = { value = it },
            keyboardType = KeyboardType.Number,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        TextButton(
            onClick = { viewModel.saveWallet(name, value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}