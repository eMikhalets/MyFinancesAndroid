package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionsScreen(
    navController: NavHostController,
    viewModel: TransactionsVM = hiltViewModel()
) {
    val state = viewModel.state

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)
    val tabs = listOf(
        stringResource(R.string.expenses),
        stringResource(R.string.incomes)
    )

    LaunchedEffect("init_key") {
        viewModel.getTransactions()
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_transactions)
    ) {
        Column(Modifier.fillMaxSize()) {
            AppPager(
                navController = navController,
                scope = scope,
                pagerState = pagerState,
                tabs = tabs,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TransactionsList(
                        navController = navController,
                        list = state.expenseList
                    )
                    1 -> TransactionsList(
                        navController = navController,
                        list = state.incomeList
                    )
                }
            }
            AddTransaction(
                navController = navController,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}