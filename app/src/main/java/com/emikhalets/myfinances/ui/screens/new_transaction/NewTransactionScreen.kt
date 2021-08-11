package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.foundation.background
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
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.utils.enums.KeyboardKey
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    when (val state = viewModel.state) {
        is NewTransactionState.Categories -> {
            NewTransactionScreen(
                categories = state.list,
                transactionType = transactionType,
                selectedCategory = selectedCategory,
                onCategoryChange = { selectedCategory = it },
                note = note,
                value = value,
                onNoteChange = { note = it },
                onValueChange = { value = it }
            )
        }
        is NewTransactionState.Error -> {
        }
        NewTransactionState.Loading -> {
        }
        NewTransactionState.Idle -> {
            viewModel.getCategories(transactionType)
            NewTransactionScreen(
                categories = emptyList(),
                transactionType = transactionType,
                selectedCategory = selectedCategory,
                onCategoryChange = { selectedCategory = it },
                note = note,
                value = value,
                onNoteChange = { note = it },
                onValueChange = { value = it }
            )
        }
    }
}

@Composable
fun NewTransactionScreen(
    categories: List<Category>,
    transactionType: TransactionType,
    selectedCategory: Category?,
    onCategoryChange: (Category) -> Unit,
    note: String,
    value: String,
    onNoteChange: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    var addCategoryDialog by remember { mutableStateOf(false) }
    val label = when (transactionType) {
        TransactionType.EXPENSE -> stringResource(R.string.new_expense)
        TransactionType.INCOME -> stringResource(R.string.new_income)
    }

    Column {
        Text(
            text = label,
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
                modifier = Modifier
                    .weight(1f)
                    .clickable {}
            )
            NewTransactionButton(
                label = stringResource(R.string.category),
                name = selectedCategory?.name ?: stringResource(R.string.no_categories),
                icon = R.drawable.ic_coins,
                color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                modifier = Modifier
                    .weight(1f)
                    .clickable { addCategoryDialog = true }
            )
        }
        TextField(
            value = note,
            onValueChange = { onNoteChange(it) },
            label = { Text(stringResource(R.string.note)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(stringResource(R.string.value)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
    if (addCategoryDialog) {
        ChooseCategoryDialog(
            categories = categories,
            onSelect = { onCategoryChange(it) },
            onDismiss = { addCategoryDialog = it }
        )
    }
}

@Composable
fun NewTransactionButton(
    label: String,
    name: String,
    icon: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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