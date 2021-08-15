package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListsScreen(
    navController: NavHostController,
    viewModel: ListsVM = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 3)
    val tabs = listOf(
        stringResource(R.string.wallets),
        stringResource(R.string.category_expense),
        stringResource(R.string.category_income)
    )
    var addCategoryDialog by remember { mutableStateOf(false) }
    var selectedCategoryType by remember { mutableStateOf(TransactionType.Expense) }

    LaunchedEffect("init_key") {
        viewModel.getWallets()
        viewModel.getCategoriesExpense()
        viewModel.getCategoriesIncome()
    }

    LaunchedEffect(state) {
        if (state.categoryExpenseSaved) viewModel.getCategoriesExpense()
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_lists)
    ) {
        Column(Modifier.fillMaxSize()) {
            AppPager(
                navController = navController,
                scope = scope,
                pagerState = pagerState,
                tabs = tabs,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> WalletsList(
                        navController = navController,
                        list = state.wallets,
                        onAddClick = {}
                    )
                    1 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesExpense,
                        onAddClick = {
                            selectedCategoryType = TransactionType.Expense
                            addCategoryDialog = true
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesIncome,
                        onAddClick = {
                            selectedCategoryType = TransactionType.Income
                            addCategoryDialog = true
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        if (addCategoryDialog) {
            AddCategoryDialog(
                onSave = {
                    addCategoryDialog = false
                    viewModel.saveCategory(selectedCategoryType, it)
                }
            )
        }
    }
}