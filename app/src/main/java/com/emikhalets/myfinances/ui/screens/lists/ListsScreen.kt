package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppAddButton
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
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

    LaunchedEffect("init_key") {
        viewModel.getData()
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
                        onAddClick = {},
                        modifier = Modifier.fillMaxSize()
                    )
                    1 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesExpense,
                        onAddClick = {},
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> CategoriesList(
                        navController = navController,
                        list = state.categoriesIncome,
                        onAddClick = {},
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun WalletsList(
    navController: NavHostController,
    list: List<Wallet>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (list.isEmpty()) {
        Column {
            AppAddButton(
                label = stringResource(R.string.new_wallet),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onAddClick() }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize(),
                content = { Text(stringResource(R.string.empty_wallets)) }
            )
        }
    } else {
        Column {
            AppAddButton(
                label = stringResource(R.string.new_wallet),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onAddClick() }
            )
            LazyColumn(modifier.fillMaxSize()) {
                items(list) { wallet ->
                    ListsWalletItem(
                        name = wallet.name,
                        value = wallet.amount
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesList(
    navController: NavHostController,
    list: List<Category>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (list.isEmpty()) {
        Column {
            AppAddButton(
                label = stringResource(R.string.new_category),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onAddClick() }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize(),
                content = { Text(stringResource(R.string.empty_categories)) }
            )
        }
    } else {
        Column {
            AppAddButton(
                label = stringResource(R.string.new_category),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onAddClick() }
            )
            LazyColumn(modifier.fillMaxSize()) {
                items(list) {
                }
            }
        }
    }
}

@Composable
fun ListsWalletItem(
    name: String,
    value: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_wallet),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(text = name)
            Spacer(Modifier.width(8.dp))
            Text(text = value.toString())
        }
    }
}