package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.utils.enums.KeyboardKey
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getLabel
import com.emikhalets.myfinances.utils.navigateBack

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    val state = viewModel.state
    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var selectedWallet by remember { mutableStateOf<Wallet?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var addCategoryDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = transactionType.getLabel(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Row(Modifier.fillMaxWidth()) {
            NewTransactionButton(
                label = stringResource(R.string.wallet),
                name = selectedCategory?.name ?: stringResource(R.string.no_categories),
                icon = R.drawable.ic_wallet,
                color = MaterialTheme.colors.primary.copy(alpha = 0.8f),
                error = state.walletError,
                modifier = Modifier
                    .weight(1f)
                    .clickable {}
            )
            NewTransactionButton(
                label = stringResource(R.string.category),
                name = selectedCategory?.name ?: stringResource(R.string.no_categories),
                icon = R.drawable.ic_coins,
                color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                error = state.categoryError,
                modifier = Modifier
                    .weight(1f)
                    .clickable { addCategoryDialog = true }
            )
        }
        AppTextField(
            label = stringResource(R.string.note),
            value = note,
            onValueChange = { note = it },
            keyboardType = KeyboardType.Text,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        AppTextField(
            label = stringResource(R.string.value),
            value = value,
            onValueChange = { value = it },
            keyboardType = KeyboardType.Number,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { navController.navigateBack() },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            TextButton(
                onClick = {
                    viewModel.saveTransaction(
                        selectedWallet?.walletId,
                        selectedCategory?.categoryId,
                        note,
                        value,
                        transactionType
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
    if (addCategoryDialog) {
        ChooseCategoryDialog(
            categories = state.categories,
            onSelect = { selectedCategory = it },
            onDismiss = { addCategoryDialog = it }
        )
    }
}

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        modifier = modifier
    )
}

@Composable
fun NewTransactionButton(
    label: String,
    name: String,
    icon: Int,
    color: Color,
    error: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (error) Color.Red else Color.Transparent
            )
            .background(color = color)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun NewTransactionKeyboard(
    onClickKey: (Int) -> Unit
) {
    Column(Modifier.padding(4.dp)) {
        Row {
            KeyboardButton(
                value = KeyboardKey.Num1,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num2,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num3,
                onClickKey = {}
            )
        }
        Row {
            KeyboardButton(
                value = KeyboardKey.Num4,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num5,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num6,
                onClickKey = {}
            )
        }
        Row {
            KeyboardButton(
                value = KeyboardKey.Num7,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num8,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Num9,
                onClickKey = {}
            )
        }
        Row {
            KeyboardButton(
                value = KeyboardKey.Num0,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Dot,
                onClickKey = {}
            )
            KeyboardButton(
                value = KeyboardKey.Delete,
                onClickKey = {}
            )
        }
    }
}

@Composable
fun KeyboardButton(
    value: KeyboardKey,
    onClickKey: (KeyboardKey) -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.padding(4.dp),
        onClick = { onClickKey(value) }
    ) {
        Text(value.value)
    }
}