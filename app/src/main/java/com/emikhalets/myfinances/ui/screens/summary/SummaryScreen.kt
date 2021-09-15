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
import com.emikhalets.myfinances.ui.base.AppPager
import com.emikhalets.myfinances.ui.base.AppText
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.getCurrentWalletId
import com.emikhalets.myfinances.utils.sectionBorder
import com.emikhalets.myfinances.utils.toast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SummaryScreen(
    navController: NavHostController,
    viewModel: SummaryVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    val date by remember { mutableStateOf(Date()) }
    val budget by remember { mutableStateOf("") }
    val expense by remember { mutableStateOf("") }
    val income by remember { mutableStateOf("") }

    LaunchedEffect("init") {
        viewModel.getSummaryMonth(context.getCurrentWalletId())
    }
    LaunchedEffect(state) {
        if (state.error != null) toast(context, state.error)
    }

    SummaryScreen(
        navController = rememberNavController(),
        date = Date(),
        budget = budget,
        expense = expense,
        income = income,
        summaryExpenses = emptyList(),
        summaryIncomes = emptyList()
    )
}

@Composable
fun SummaryScreen(
    navController: NavHostController,
    date: Date,
    budget: String,
    expense: String,
    income: String,
    summaryExpenses: List<SummaryTransaction>,
    summaryIncomes: List<SummaryTransaction>
) {
    val dateText = SimpleDateFormat("MMMM, y", Locale.getDefault()).format(date)

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_summary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppText(
                text = dateText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
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
fun SummaryExpenseIncome(expense: String, income: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .sectionBorder()
    ) {
        AppText(
            text = expense,
            color = Color.Red,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
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
        AppText(
            text = income,
            color = Color.Green,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        )
    }
}

@Composable
fun SummaryBudget(budget: String, expense: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
        AppText(
            text = budget,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
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
        AppText(
            text = "${budget.toDouble() - expense.toDouble()}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
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

    Column(Modifier.fillMaxWidth()) {
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
            date = Date(),
            budget = "20000",
            expense = "14562.18",
            income = "61487.12",
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