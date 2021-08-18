package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.utils.enums.AppIcon
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigation.navigateToNewTransaction
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colors.surface
                            )
                        )
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                AddTransactionButton(
                    icon = AppIcon.Minus.icon,
                    onClick = { navController.navigateToNewTransaction(TransactionType.Expense) }
                )
                Spacer(modifier = Modifier.width(50.dp))
                AddTransactionButton(
                    icon = AppIcon.Plus.icon,
                    onClick = { navController.navigateToNewTransaction(TransactionType.Income) }
                )
            }
        }
    }
}