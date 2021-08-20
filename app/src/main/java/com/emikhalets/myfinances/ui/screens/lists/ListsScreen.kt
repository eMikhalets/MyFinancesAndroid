package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.utils.AnimateFadeInOut
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListsScreen(
    navController: NavHostController,
    viewModel: ListsVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 3)

    var showAddingCategory by remember { mutableStateOf(false) }
    var showAddingWallet by remember { mutableStateOf(false) }
    var selectedCategoryType by remember { mutableStateOf(TransactionType.Expense) }

    LaunchedEffect("init") {
        viewModel.getWallets()
        viewModel.getCategoriesExpense()
        viewModel.getCategoriesIncome()
    }
    LaunchedEffect(state) {
        if (state.savedCategoryExpense) viewModel.getCategoriesExpense()
        if (state.savedCategoryIncome) viewModel.getCategoriesIncome()
        if (state.savedWallet) viewModel.getWallets()
        if (state.error != null) toast(context, state.errorMessage())
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_lists)
    ) {
        Column(Modifier.fillMaxSize()) {
            AppPager(
                scope = scope,
                pagerState = pagerState,
                tabs = listOf(
                    stringResource(R.string.wallets),
                    stringResource(R.string.category_expense),
                    stringResource(R.string.category_income)
                )
            ) { page ->
                when (page) {
                    0 -> WalletsList(
                        list = state.wallets,
                        onAddClick = { showAddingWallet = true }
                    )
                    1 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesExpense,
                        onAddClick = {
                            selectedCategoryType = TransactionType.Expense
                            showAddingCategory = true
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesIncome,
                        onAddClick = {
                            selectedCategoryType = TransactionType.Income
                            showAddingCategory = true
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        AnimateFadeInOut(visible = showAddingWallet, duration = 300) {
            AddWalletDialog(
                onSave = { name, value ->
                    showAddingWallet = false
                    viewModel.saveWallet(name, value)
                },
                onDismiss = { showAddingWallet = false }
            )
        }
        AnimateFadeInOut(visible = showAddingCategory, duration = 300) {
            AddCategoryDialog(
                onSave = { name, icon ->
                    showAddingCategory = false
                    viewModel.saveCategory(selectedCategoryType, name, icon)
                },
                onDismiss = { showAddingCategory = false }
            )
        }
    }
}