package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@Composable
fun TransactionsScreen(
    navController: NavHostController,
    viewModel: TransactionsVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect("init") {
        viewModel.getExpenseTransactions()
        viewModel.getIncomeTransactions()
    }
    LaunchedEffect(state) {
        if (state.error != null) toast(context, state.errorMessage())
    }

    TransactionsScreen(
        navController = navController,
        expenseList = state.expenseList,
        incomeList = state.incomeList
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionsScreen(
    navController: NavHostController,
    expenseList: List<Transaction>,
    incomeList: List<Transaction>
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_transactions)
    ) {
        Column(Modifier.fillMaxSize()) {
            AppPager(
                scope = scope,
                pagerState = pagerState,
                tabs = listOf(
                    stringResource(R.string.expenses),
                    stringResource(R.string.incomes)
                ),
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TransactionsList(
                        navController = navController,
                        list = expenseList
                    )
                    1 -> TransactionsList(
                        navController = navController,
                        list = incomeList
                    )
                }
            }
            AddButtonsLayout(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        TransactionsScreen(
            navController = rememberNavController(),
            expenseList = emptyList(),
            incomeList = emptyList()
        )
    }
}