package com.emikhalets.presentation.screens.currencies

import androidx.compose.runtime.Composable

@Composable
fun CurrenciesScreen(
    onBackClick: () -> Unit,
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