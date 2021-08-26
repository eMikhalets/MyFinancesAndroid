package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
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

    var month by remember { mutableStateOf(0) }
    var year by remember { mutableStateOf(2000) }

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
        incomeList = state.incomeList,
        month = month,
        year = year,
        onMonthChange = {
            if (month != it) {
                month = it
                //update transactions
            }
        },
        onYearChange = {
            if (year != it) {
                year = it
                //update transactions
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionsScreen(
    navController: NavHostController,
    expenseList: List<Transaction>,
    incomeList: List<Transaction>,
    month: Int,
    year: Int,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_transactions)
    ) {
        Column(Modifier.fillMaxSize()) {
            DateChooser(
                month = month,
                year = year,
                onMonthChange = onMonthChange,
                onYearChange = onYearChange
            )
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
            incomeList = emptyList(),
            month = 5,
            year = 2045,
            onMonthChange = {},
            onYearChange = {}
        )
    }
}