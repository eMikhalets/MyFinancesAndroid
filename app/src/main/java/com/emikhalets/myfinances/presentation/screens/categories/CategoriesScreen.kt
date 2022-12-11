package com.emikhalets.myfinances.presentation.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
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
import com.emikhalets.myfinances.presentation.core.AppButton
import com.emikhalets.myfinances.presentation.core.AppScaffold
import com.emikhalets.myfinances.presentation.core.TextPrimary
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.textSecondary
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toast

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var categoryDialogVisible by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    LaunchedEffect(state.error) {
        toast(context, state.error)
    }

    AppScaffold(navController) {
        CategoriesScreen(
            incomeList = state.incomeList,
            expenseList = state.expenseList,
            onCategoryClick = {
                category = it
                categoryDialogVisible = true
            },
            onAddClick = {
                category = null
                categoryDialogVisible = true
            }
        )
    }

    if (categoryDialogVisible) {
        CategoryDialog(
            category = category,
            onDismiss = { categoryDialogVisible = false },
            onSaveClick = viewModel::saveCategory,
            onDeleteClick = viewModel::deleteCategory
        )
    }
}

@Composable
private fun CategoriesScreen(
    incomeList: List<Category>,
    expenseList: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TextPrimary(
                    text = stringResource(id = R.string.app_incomes),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Divider(color = MaterialTheme.colors.textSecondary)
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(incomeList) { category ->
                        CategoryItem(category, onCategoryClick)
                    }
                }
            }
            Divider(color = MaterialTheme.colors.textSecondary)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TextPrimary(
                    text = stringResource(id = R.string.app_expenses),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Divider(color = MaterialTheme.colors.textSecondary)
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(expenseList) { category ->
                        CategoryItem(category, onCategoryClick)
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AppButton(
                text = stringResource(R.string.app_add),
                onClick = { onAddClick() }
            )
        }
    }
}

@Composable
private fun CategoryItem(category: Category, onCategoryClick: (Category) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        TextPrimary(
            text = category.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCategoryClick(category) }
                .padding(8.dp)
        )
        Divider(color = MaterialTheme.colors.textSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        CategoriesScreen(
            incomeList = listOf(
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
            ),
            expenseList = listOf(
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
                Category(name = "Name 1", type = TransactionType.Income),
            ),
            onCategoryClick = {},
            onAddClick = {}
        )
    }
}