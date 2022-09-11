package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.presentation.core.AppPager
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.MainToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.core.TextMaxSize
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.toDate
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    var transactionDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getTransactions()
        viewModel.getCategories()
    }

    LaunchedEffect(state.transaction) {
        state.transaction?.let { transactionDialogVisible = true }
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    MainScreen(
        navController = navController,
        incomeList = state.incomeList,
        expenseList = state.expenseList,
        onTransactionClick = viewModel::getTransaction,
        onAddClick = { transactionDialogVisible = true }
    )

    if (transactionDialogVisible) {
        TransactionDialog(
            entity = state.transaction,
            categories = state.categories,
            onDismiss = { transactionDialogVisible = false },
            onSaveClick = viewModel::saveTransaction,
            onDeleteClick = viewModel::deleteTransaction
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainScreen(
    navController: NavHostController,
    incomeList: List<TransactionEntity>,
    expenseList: List<TransactionEntity>,
    onTransactionClick: (Long) -> Unit,
    onAddClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)

    ScreenScaffold(toolbar = { MainToolbar(navController) }) {
        Column(Modifier.fillMaxWidth()) {
            AppPager(
                scope = scope,
                pagerState = pagerState,
                tabs = listOf(stringResource(R.string.expenses), stringResource(R.string.incomes)),
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TransactionsList(expenseList, onTransactionClick)
                    1 -> TransactionsList(incomeList, onTransactionClick)
                }
            }
            AddButton(onAddClick)
        }
    }
}

@Composable
private fun TransactionsList(
    transactions: List<TransactionEntity>,
    onTransactionClick: (Long) -> Unit,
) {
    if (transactions.isEmpty()) {
        TextMaxSize(stringResource(R.string.no_transactions))
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionsItem(transaction, onTransactionClick)
            }
        }
    }
}

@Composable
private fun TransactionsItem(entity: TransactionEntity, onTransactionClick: (Long) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onTransactionClick(entity.transaction.id) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AppText(
                    text = entity.category.name,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                AppText(
                    text = entity.transaction.timestamp.toDate(),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                AppText(
                    text = entity.transaction.note,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AppText(
                text = stringResource(R.string.app_money_value, entity.transaction.value),
                fontSize = 20.sp,
            )
        }
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
        MainScreen(
            navController = rememberNavController(),
            incomeList = PreviewEntities.getMainScreenIncomeList(),
            expenseList = PreviewEntities.getMainScreenExpenseList(),
            onTransactionClick = {},
            onAddClick = { }
        )
    }
}