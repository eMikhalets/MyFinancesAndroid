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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType
) {
    var note by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val categories = listOf(
        Category("test1", 1, R.drawable.ic_attach_money),
        Category("test2", 1, R.drawable.ic_attach_money),
        Category("test3", 1, R.drawable.ic_attach_money),
        Category("test4", 1, R.drawable.ic_attach_money),
        Category("test5", 1, R.drawable.ic_attach_money),
        Category("test6", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test7", 1, R.drawable.ic_attach_money),
        Category("test8", 1, R.drawable.ic_attach_money)
    )

    NewTransactionScreen(
        categories = categories,
        selectedCategory = selectedCategory,
        onCategoryChange = { selectedCategory = it },
        note = note,
        onNoteChange = { note = it }
    )
}

@Composable
fun NewTransactionScreen(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategoryChange: (Category) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit
) {
    var addCategoryDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = stringResource(R.string.new_transaction),
            modifier = Modifier.fillMaxWidth()
        )
        CategoryButton(
            name = selectedCategory?.name ?: "name",
            icon = painterResource(selectedCategory?.icon ?: R.drawable.ic_attach_money),
            modifier = Modifier.clickable { addCategoryDialog = true }
        )
        TextField(
            value = note,
            onValueChange = { onNoteChange(it) },
            label = { Text(stringResource(R.string.note)) },
            modifier = Modifier.fillMaxWidth()
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
    Row(modifier = modifier) {
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