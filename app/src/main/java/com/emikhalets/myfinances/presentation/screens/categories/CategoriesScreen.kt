package com.emikhalets.myfinances.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.presentation.core.AppPager
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    var categoryDialogVisible by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) { viewModel.getCategories() }

    LaunchedEffect(state.expenseList) {
        if (category?.type == TransactionType.Expense) {
            category = state.expenseList.find { it.id == category?.id }
        }
    }

    LaunchedEffect(state.incomeList) {
        if (category?.type == TransactionType.Income) {
            category = state.incomeList.find { it.id == category?.id }
        }
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    CategoriesScreen(
        navController = navController,
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

    if (categoryDialogVisible) {
        CategoryDialog(
            category = category,
            onDismiss = { categoryDialogVisible = false },
            onSaveClick = viewModel::saveCategory,
            onDeleteClick = viewModel::deleteCategory
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CategoriesScreen(
    navController: NavHostController,
    incomeList: List<Category>,
    expenseList: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)

    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_categories_screen))
    }) {
        Column(Modifier.fillMaxWidth()) {
            AppPager(
                scope = scope,
                pagerState = pagerState,
                tabs = listOf(stringResource(R.string.expenses), stringResource(R.string.incomes)),
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> CategoriesList(expenseList, onCategoryClick)
                    1 -> CategoriesList(incomeList, onCategoryClick)
                }
            }
            AddButton(onAddClick)
        }
    }
}

@Composable
private fun CategoriesList(categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    val list = categories.toMutableList().apply { add(Category.getDefaultOld()) }
    LazyColumn(Modifier.fillMaxSize()) {
        items(list) { category ->
            CategoryItem(category, onCategoryClick)
        }
    }
}

@Composable
private fun CategoryItem(category: Category, onCategoryClick: (Category) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        AppText(
            text = category.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCategoryClick(category) }
                .padding(8.dp)
        )
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
private fun AddButton(onAddClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .clickable { onAddClick() }
            .padding(16.dp)
    ) {
        AppText(
            text = stringResource(R.string.add_transaction),
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        CategoriesScreen(
            navController = rememberNavController(),
            incomeList = PreviewEntities.getCategoriesScreenIncomeList(),
            expenseList = PreviewEntities.getCategoriesScreenExpenseList(),
            onCategoryClick = {},
            onAddClick = {}
        )
    }
}