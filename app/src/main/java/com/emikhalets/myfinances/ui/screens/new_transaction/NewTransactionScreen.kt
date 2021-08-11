package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    var note by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    when (val state = viewModel.state) {
        is NewTransactionState.Categories -> {
            NewTransactionScreen(
                categories = state.list,
                transactionType = transactionType,
                selectedCategory = selectedCategory,
                onCategoryChange = { selectedCategory = it },
                note = note,
                onNoteChange = { note = it }
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
                onNoteChange = { note = it }
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
    onNoteChange: (String) -> Unit
) {
    var addCategoryDialog by remember { mutableStateOf(false) }
    val label = when (transactionType) {
        TransactionType.EXPENSE -> stringResource(R.string.new_expense)
        TransactionType.INCOME -> stringResource(R.string.new_income)
    }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = label,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryButton(
            name = selectedCategory?.name ?: stringResource(R.string.no_categories),
            icon = painterResource(selectedCategory?.icon ?: R.drawable.ic_coins),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { addCategoryDialog = true }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = note,
            onValueChange = { onNoteChange(it) },
            label = { Text(stringResource(R.string.note)) },
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
fun CategoryButton(
    name: String,
    icon: Painter,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding(8.dp)) {
        Icon(
            painter = icon,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name
        )
    }
}