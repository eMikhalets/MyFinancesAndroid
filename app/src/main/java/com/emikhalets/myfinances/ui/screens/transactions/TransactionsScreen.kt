package com.emikhalets.myfinances.ui.screens.transactions

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.AppIcon
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.AppText
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.month
import com.emikhalets.myfinances.utils.months
import com.emikhalets.myfinances.utils.toast
import com.emikhalets.myfinances.utils.year
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import java.util.*

@Composable
fun TransactionsScreen(
    navController: NavHostController,
    viewModel: TransactionsVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var month by remember { mutableStateOf(Calendar.getInstance().month()) }
    var year by remember { mutableStateOf(Calendar.getInstance().year()) }

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
        onBackDateClick = {
            val new = Calendar.getInstance()
            new.set(Calendar.MONTH, month)
            new.add(Calendar.MONTH, -1)
            month = new.month()
            if (month == 11) year--
            Log.d("TAG", "onDateChange: $month")
        },
        onForwardDateClick = {
            val new = Calendar.getInstance()
            new.set(Calendar.MONTH, month)
            new.add(Calendar.MONTH, 1)
            month = new.month()
            if (month == 0) year++
            Log.d("TAG", "onDateChange: $month")
            // viewModel.getTransactions with new date
        },
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
    onBackDateClick: () -> Unit,
    onForwardDateClick: () -> Unit
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
                onBackDateClick = onBackDateClick,
                onForwardDateClick = onForwardDateClick
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DateChooser(
    month: Int,
    year: Int,
    onBackDateClick: () -> Unit,
    onForwardDateClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AppIcon(
            icon = R.drawable.ic_arrow_back,
            modifier = Modifier
                .clickable { onBackDateClick() }
                .padding(16.dp)
        )
//        AnimatedContent(
//            targetState = date,
//            transitionSpec = {
//                if (targetState > initialState) {
//                    slideInHorizontally({ width -> width }) + fadeIn() with
//                            slideOutHorizontally({ width -> -width }) + fadeOut()
//                } else {
//                    slideInHorizontally({ width -> -width }) + fadeIn() with
//                            slideOutHorizontally({ width -> width }) + fadeOut()
//                }.using(
//                    SizeTransform(clip = false)
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//                .padding(8.dp)
//        ) { targetDate ->
//            AppText(
//                text = "${targetDate.monthName()}, ${targetDate.year()}",
//                fontSize = 18.sp,
//                textAlign = TextAlign.Center
//            )
//        }
        AppText(
            text = "${months()[month]}, $year",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        )
        AppIcon(
            icon = R.drawable.ic_arrow_forward,
            modifier = Modifier
                .clickable { onForwardDateClick() }
                .padding(16.dp)
        )
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
            month = Calendar.getInstance().month(),
            year = Calendar.getInstance().year(),
            onBackDateClick = {},
            onForwardDateClick = {}
        )
    }
}