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
import com.emikhalets.myfinances.presentation.core.MainToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.core.TextPrimary
import com.emikhalets.myfinances.presentation.core.TextPrimaryFillSize
import com.emikhalets.myfinances.presentation.core.TextSecondary
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.presentation.theme.textSecondary
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.enums.TransactionType
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
    var entity by remember { mutableStateOf<TransactionEntity?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getTransactions()
        viewModel.getCategories()
    }

    LaunchedEffect(state.expenseList) {
        if (entity?.transaction?.type == TransactionType.Expense) {
            entity = state.expenseList.find { it.transaction.id == entity?.transaction?.id }
        }
    }

    LaunchedEffect(state.incomeList) {
        if (entity?.transaction?.type == TransactionType.Income) {
            entity = state.incomeList.find { it.transaction.id == entity?.transaction?.id }
        }
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    MainScreen(
        navController = navController,
        incomeList = state.incomeList,
        expenseList = state.expenseList,
        onTransactionClick = {
            entity = it
            transactionDialogVisible = true
        },
        onAddClick = {
            entity = null
            transactionDialogVisible = true
        }
    )

    if (transactionDialogVisible) {
        TransactionDialog(
            entity = entity,
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
    onTransactionClick: (TransactionEntity) -> Unit,
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
    onTransactionClick: (TransactionEntity) -> Unit,
) {
    if (transactions.isEmpty()) {
        TextPrimaryFillSize(stringResource(R.string.no_transactions))
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionsItem(transaction, onTransactionClick)
            }
        }
    }
}

@Composable
private fun TransactionsItem(
    entity: TransactionEntity,
    onTransactionClick: (TransactionEntity) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTransactionClick(entity) }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TextPrimary(
                    text = entity.category.name,
                    size = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                TextSecondary(
                    text = entity.transaction.timestamp.toDate(),
                    size = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                TextSecondary(
                    text = entity.transaction.note,
                    size = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            TextPrimary(
                text = stringResource(R.string.app_money_value, entity.transaction.value),
                size = 20.sp,
            )
        }
        Divider(color = MaterialTheme.colors.textSecondary)
    }
}

@Composable
private fun AddButton(onAddClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.boxBackground)
            .clickable { onAddClick() }
            .padding(16.dp)
    ) {
        TextPrimary(
            text = stringResource(R.string.add_transaction),
            size = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        MainScreen(
            navController = rememberNavController(),
            incomeList = PreviewEntities.getMainScreenIncomeList(),
            expenseList = PreviewEntities.getMainScreenExpenseList(),
            onTransactionClick = {},
            onAddClick = { }
        )
    }
}