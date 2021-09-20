package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.SummaryTransaction
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.SharedPrefs
import com.emikhalets.myfinances.utils.sectionBorder
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import java.util.*

@Composable
fun SummaryScreen(
    navController: NavHostController,
    viewModel: SummaryVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var date by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    var budget by remember { mutableStateOf(0.0) }
    var expense by remember { mutableStateOf(0.0) }
    var income by remember { mutableStateOf(0.0) }
    var currentWalletId by remember { mutableStateOf(0L) }

    LaunchedEffect("init") {
        currentWalletId = SharedPrefs.getCurrentWalletId(context)
        viewModel.getWallet(currentWalletId)
        viewModel.getSummary(date, currentWalletId)
    }
    LaunchedEffect(state) {
        state.error?.let { error -> toast(context, error) }
    }
    LaunchedEffect(state.wallet) {
        state.wallet?.let { currentWallet ->
            budget = currentWallet.budget
        }
    }
    LaunchedEffect(state.monthExpenses) {
        expense = state.monthExpenses
    }
    LaunchedEffect(state.monthIncomes) {
        income = state.monthIncomes
    }

    SummaryScreen(
        navController = rememberNavController(),
        date = date,
        onDateChange = {
            date = it
            viewModel.getSummary(date, currentWalletId)
        },
        budget = budget,
        expense = expense,
        income = income,
        summaryExpenses = state.summaryExpenses,
        summaryIncomes = state.summaryIncomes
    )
}

@Composable
fun SummaryScreen(
    navController: NavHostController,
    date: Long,
    onDateChange: (Long) -> Unit,
    budget: Double,
    expense: Double,
    income: Double,
    summaryExpenses: List<SummaryTransaction>,
    summaryIncomes: List<SummaryTransaction>
) {

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_summary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DateChooser(date = date, onDateChange = onDateChange)
            Spacer(Modifier.height(8.dp))
            SummaryExpenseIncome(expense = expense, income = income)
            Spacer(Modifier.height(16.dp))
            SummaryBudget(budget = budget, expense = expense)
            Spacer(Modifier.height(16.dp))
            SummaryTransactionOnCategories(
                summaryExpenses = summaryExpenses,
                summaryIncomes = summaryIncomes
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SummaryExpenseIncome(expense: Double, income: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(start = 16.dp, end = 16.dp)
            .sectionBorder()
    ) {
        AppTextMoney(
            value = expense,
            fontColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        )
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp)
                .width(1.dp)
        )
        AppTextMoney(
            value = income,
            fontColor = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        )
    }
}

@Composable
fun SummaryBudget(budget: Double, expense: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .sectionBorder()
            .padding(8.dp)
    ) {
        AppText(
            text = stringResource(R.string.budget_on_month),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        AppTextMoney(
            value = budget,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        AppText(
            text = stringResource(R.string.budget_balance),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        AppTextMoney(
            value = budget - expense,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SummaryTransactionOnCategories(
    summaryExpenses: List<SummaryTransaction>,
    summaryIncomes: List<SummaryTransaction>
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        AppText(
            text = stringResource(R.string.transactions_on_categories),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        AppPager(
            scope = scope,
            pagerState = pagerState,
            tabs = listOf(stringResource(R.string.expenses), stringResource(R.string.incomes))
        ) { page ->
            when (page) {
                0 -> {
                    SummaryOnCategoryList(transactions = summaryExpenses)
                }
                1 -> {
                    SummaryOnCategoryList(transactions = summaryIncomes)
                }
            }
        }
    }
}

@Composable
fun SummaryOnCategoryList(transactions: List<SummaryTransaction>) {
    Column {
        transactions.forEach { summary ->
            SummaryOnCategoryItem(category = summary.category, value = summary.value)
        }
    }
}

@Composable
fun SummaryOnCategoryItem(category: Category, value: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppText(
            text = category.name,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        AppText(
            text = value.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        SummaryScreen(
            navController = rememberNavController(),
            date = Calendar.getInstance().timeInMillis,
            onDateChange = {},
            budget = 20000.0,
            expense = 14562.0,
            income = 61487.12,
            summaryExpenses = listOf(
                SummaryTransaction(154.23, Category(name = "category_1")),
                SummaryTransaction(154.23, Category(name = "category_2")),
                SummaryTransaction(154.23, Category(name = "category_3")),
                SummaryTransaction(154.23, Category(name = "category_4")),
                SummaryTransaction(154.23, Category(name = "category_5"))
            ),
            summaryIncomes = emptyList()
        )
    }
}