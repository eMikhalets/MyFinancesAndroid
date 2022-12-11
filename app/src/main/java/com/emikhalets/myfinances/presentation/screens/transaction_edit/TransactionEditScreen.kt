package com.emikhalets.myfinances.presentation.screens.transaction_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
import com.emikhalets.myfinances.domain.entity.copyOrNew
import com.emikhalets.myfinances.presentation.core.AppCategorySpinner
import com.emikhalets.myfinances.presentation.core.AppScaffold
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.TextPrimary
import com.emikhalets.myfinances.presentation.core.TransactionTypeChooser
import com.emikhalets.myfinances.presentation.core.TransactionKeyboard
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toast

@Composable
fun TransactionEditScreen(
    navController: NavHostController,
    viewModel: TransactionEditViewModel = hiltViewModel(),
    transactionId: Long?,
    transactionType: TransactionType?,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        transactionId?.let { viewModel.getTransaction(it) }
    }

    LaunchedEffect(state.entity) {
        viewModel.getCategories(transactionType ?: state.entity?.transaction?.type)
    }

    LaunchedEffect(state.error) {
        toast(context, state.error)
    }

    LaunchedEffect(state.saved) {
        if (state.saved) navController.popBackStack()
    }

    AppScaffold(navController) {
        TransactionEditScreen(entity = state.entity,
            categories = state.categories,
            passedType = transactionType,
            onTypeChange = { viewModel.getCategories(it) },
            onSaveClick = { viewModel.saveTransaction(it) })
    }
}

@Composable
private fun TransactionEditScreen(
    entity: TransactionEntity?,
    categories: List<Category>,
    passedType: TransactionType?,
    onTypeChange: (TransactionType) -> Unit,
    onSaveClick: (Transaction) -> Unit,
) {
    var type by remember {
        mutableStateOf(entity?.transaction?.type ?: passedType ?: TransactionType.Expense)
    }
    var category by remember {
        mutableStateOf(entity?.category ?: Category.getDefaultInstance(type))
    }
    var value by remember { mutableStateOf(entity?.transaction?.value ?: 0.0) }
    var note by remember { mutableStateOf(entity?.transaction?.note ?: "") }

    Column(Modifier.fillMaxSize()) {
        TransactionTypeChooser(type = type, onTypeSelect = {
            type = it
            onTypeChange(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        AppCategorySpinner(categories = categories,
            initItem = category,
            onSelect = { category = it })
        AppTextField(value = note, onValueChange = { note = it })
        TransactionKeyboard(value = value, onValueChange = { value = it })
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.boxBackground)
                .clickable {
                    val saveTransaction =
                        entity?.transaction.copyOrNew(category.id, value, type, note)
                    onSaveClick(saveTransaction)
                }
                .padding(16.dp)) {
            TextPrimary(text = stringResource(R.string.app_save), fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        TransactionEditScreen(entity = null,
            categories = emptyList(),
            passedType = TransactionType.Expense,
            onTypeChange = {},
            onSaveClick = {})
    }
}