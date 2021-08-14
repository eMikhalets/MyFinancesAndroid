package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigation.navigateToNewTransaction
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionsScreen(
    navController: NavHostController,
    viewModel: TransactionsVM = hiltViewModel()
) {
    val state = viewModel.state
    val pagerState = rememberPagerState(pageCount = 2)

    LaunchedEffect("init_key") {
        viewModel.getTransactions()
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_transactions)
    ) {
        Column(Modifier.fillMaxSize()) {
            TransactionsPager(
                navController = navController,
                pagerState = pagerState,
                pages = listOf("Expense", "Income"),
                expense = state.expenseList,
                income = state.incomeList
            )
            AddTransaction(
                navController = navController,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ColumnScope.TransactionsPager(
    navController: NavHostController,
    pagerState: PagerState,
    pages: List<String>,
    expense: List<Transaction>,
    income: List<Transaction>
) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        pages.forEachIndexed { index, name ->
            Tab(
                selected = pagerState.currentPage == index,
                text = {
                    Text(text = name)
                },
                onClick = { scope.launch { pagerState.scrollToPage(index) } }
            )
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
    ) { page ->
        when (page) {
            0 -> TransactionsList(
                navController = navController,
                list = expense,
                modifier = Modifier.fillMaxSize()
            )
            1 -> TransactionsList(
                navController = navController,
                list = income,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>,
    modifier: Modifier = Modifier
) {
    if (list.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = stringResource(R.string.empty_transactions))
        }
    } else {
        LazyColumn(modifier.fillMaxSize()) {
            items(list.size) {
            }
        }
    }
}

@Composable
fun AddTransaction(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Button(
            onClick = { navController.navigateToNewTransaction(TransactionType.Expense) }
        ) {
            Text(text = "Expense")
        }
        Spacer(modifier = Modifier.width(80.dp))
        Button(
            onClick = { navController.navigateToNewTransaction(TransactionType.Income) }
        ) {
            Text(text = "Income")
        }
    }
}