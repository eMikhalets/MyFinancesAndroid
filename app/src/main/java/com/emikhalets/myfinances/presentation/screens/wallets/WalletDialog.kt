package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.data.entity.copyOrNew
import com.emikhalets.myfinances.presentation.core.AppBaseDialog
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.safeToDouble

@Composable
fun WalletDialog(
    wallet: Wallet?,
    onDismiss: () -> Unit,
    onSaveClick: (Wallet) -> Unit,
    onDeleteClick: (Wallet) -> Unit,
    cancelable: Boolean = false,
) {
    val isEdit by remember { mutableStateOf(wallet != null) }

    var name by remember { mutableStateOf(wallet?.name ?: "") }
    var initValue by remember { mutableStateOf(wallet?.initValue ?: 0.0) }

    AppBaseDialog(
        label = stringResource(R.string.title_wallet_screen),
        onDismiss = { onDismiss() },
        padding = 8.dp,
        cancelable = cancelable
    ) {
        DialogLayout(
            name = name,
            initValue = initValue,
            isEdit = isEdit,
            onNameChange = { name = it },
            onInitValueChange = { initValue = it },
            onSaveClick = { onSaveClick(wallet.copyOrNew(name, initValue)) },
            onDeleteClick = { wallet?.let(onDeleteClick) }
        )
    }
}

@Composable
private fun DialogLayout(
    name: String,
    initValue: Double,
    isEdit: Boolean,
    onNameChange: (String) -> Unit,
    onInitValueChange: (Double) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            labelRes = R.string.label_name
        )
        Spacer(Modifier.height(16.dp))
        AppTextField(
            value = initValue.toString(),
            onValueChange = { onInitValueChange(it.safeToDouble()) },
            labelRes = R.string.label_init_value
        )
        Spacer(Modifier.height(16.dp))
        ControlButtons(isEdit, onSaveClick, onDeleteClick)
    }
}

@Composable
private fun ControlButtons(
    showDelete: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(Modifier.fillMaxWidth()) {
        if (showDelete) {
            AppTextButton(
                text = stringResource(R.string.delete),
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f)
            )
        }
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    MyFinancesTheme {
        AppBaseDialog(
            label = "Preview label",
            onDismiss = {},
            padding = 8.dp
        ) {
            DialogLayout(
                name = "Some name",
                initValue = 223.87,
                isEdit = true,
                onNameChange = {},
                onInitValueChange = {},
                onSaveClick = {},
                onDeleteClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogAddingPreview() {
    MyFinancesTheme {
        AppBaseDialog(
            label = "Preview label",
            onDismiss = {},
            padding = 8.dp
        ) {
            DialogLayout(
                name = "Some name",
                initValue = 223.87,
                isEdit = false,
                onNameChange = {},
                onInitValueChange = {},
                onSaveClick = {},
                onDeleteClick = {}
            )
        }
    }
}