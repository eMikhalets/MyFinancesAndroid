package com.emikhalets.myfinances.presentation.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.presentation.base.*
import com.emikhalets.myfinances.presentation.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.utils.AnimateFadeInOut
import com.emikhalets.myfinances.utils.SharedPrefs
import com.emikhalets.myfinances.utils.enums.MyIcons
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
        if (state.error != null) toast(context, state.error)
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
//            AddCategoryDialog(
//                onSave = { name, _ ->
//                    showAddingCategory = false
//                    viewModel.saveCategory(selectedCategoryType, name)
//                },
//                onDismiss = { showAddingCategory = false }
//            )
        }
    }
}

@Composable
fun WalletsList(
    list: List<Wallet>,
    onAddClick: () -> Unit
) {
    val context = LocalContext.current
    var current by remember { mutableStateOf(SharedPrefs.getCurrentWalletId(context)) }

    LaunchedEffect("init") {
    }

    Column {
        TextWithIcon(
            text = stringResource(R.string.new_wallet),
            icon = MyIcons.Plus.icon,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.secondary)
        if (list.isEmpty()) {
            TextFullScreen(stringResource(R.string.empty_wallets))
        } else {
            AppVerticalList(list) { wallet ->
                WalletListItem(
                    wallet = wallet,
                    current = current,
                    onClick = {
                        current = wallet.walletId
                    }
                )
            }
        }
    }
}

@Composable
fun WalletListItem(
    wallet: Wallet,
    current: Long,
    onClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(wallet.walletId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppIcon(
            drawable = MyIcons.Wallet.icon,
            size = 40.dp
        )
        Spacer(Modifier.width(16.dp))
        Column {
            AppText(text = wallet.name)
            if (wallet.walletId == current) {
                Spacer(Modifier.width(8.dp))
                AppText(
                    text = stringResource(R.string.by_default),
                    fontColor = MaterialTheme.colors.secondary
                )
            }
        }
//        AppTextMoney(value = wallet.value)
    }
}

@Composable
fun CategoriesList(
    navController: NavHostController,
    list: List<Category>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TextWithIcon(
            text = stringResource(R.string.new_category),
            icon = MyIcons.Plus.icon,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.secondary)
        if (list.isEmpty()) {
            TextFullScreen(stringResource(R.string.empty_categories))
        } else {
            AppVerticalList(list) { category ->
                CategoryListItem(
                    category = category,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun CategoryListItem(
    category: Category,
    onClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(category.categoryId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppIcon(
            drawable = 1,
            size = 40.dp
        )
        Spacer(Modifier.width(16.dp))
        AppText(category.name)
    }
}