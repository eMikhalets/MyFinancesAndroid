package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.core.AppIcon
import com.emikhalets.myfinances.presentation.core.AppScaffold
import com.emikhalets.myfinances.presentation.core.TextPrimary
import com.emikhalets.myfinances.presentation.navigation.AppScreen
import com.emikhalets.myfinances.presentation.navigation.navToTransactionEdit
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllTransactions()
    }

    AppScaffold(navController) {
        MainScreen(
            incomeValue = state.incomeValue,
            expenseValue = state.expenseValue,
            onExpensesClick = { navController.navigate(AppScreen.EXPENSES.route) },
            onIncomesClick = { navController.navigate(AppScreen.INCOMES.route) },
            onAddExpenseClick = {
                navController.navToTransactionEdit(null, TransactionType.Expense)
            },
            onAddIncomeClick = {
                navController.navToTransactionEdit(null, TransactionType.Income)
            },
            onCategoriesClick = {
                navController.navigate(AppScreen.CATEGORIES.route)
            },
            onChartsClick = {},
            onBudgetClick = {},
        )
    }
}

@Composable
private fun MainScreen(
    incomeValue: Double,
    expenseValue: Double,
    onExpensesClick: () -> Unit,
    onIncomesClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onChartsClick: () -> Unit,
    onBudgetClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            ValueBox(incomeValue, TransactionType.Income, onIncomesClick)
            ValueBox(expenseValue, TransactionType.Expense, onExpensesClick)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            MainButton(
                text = stringResource(R.string.app_add_expense),
                icon = Icons.Default.HorizontalRule,
                onClick = onAddExpenseClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = stringResource(R.string.app_add_income),
                icon = Icons.Default.Add,
                onClick = onAddIncomeClick
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        ) {
            MainButton(
                text = stringResource(R.string.app_categories),
                icon = Icons.Default.Bookmark,
                onClick = onCategoriesClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = stringResource(R.string.app_budget),
                icon = Icons.Default.BarChart,
                onClick = onChartsClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = stringResource(R.string.app_charts),
                icon = Icons.Default.HorizontalRule,
                onClick = onBudgetClick
            )
        }
    }
}

@Composable
private fun ValueBox(value: Double, type: TransactionType, onClick: () -> Unit) {
    val title by remember { mutableStateOf(getValueBoxTitle(type)) }
    val valuePrefix by remember { mutableStateOf(getValueBoxPrefix(type)) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.boxBackground, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(150.dp)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        TextPrimary(
            text = stringResource(id = title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextPrimary(
            text = "${stringResource(valuePrefix)} $value",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun RowScope.MainButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.boxBackground, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f)
    ) {
        AppIcon(
            imageVector = icon,
            modifier = Modifier.size(50.dp)
        )
        TextPrimary(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppScaffold(rememberNavController()) {
            MainScreen(
                incomeValue = 7057.64,
                expenseValue = 7057.64,
                onExpensesClick = {},
                onIncomesClick = {},
                onAddExpenseClick = {},
                onAddIncomeClick = {},
                onCategoriesClick = {},
                onChartsClick = {},
                onBudgetClick = {},
            )
        }
    }
}

private fun getValueBoxTitle(type: TransactionType): Int {
    return when (type) {
        TransactionType.Expense -> R.string.app_expenses
        TransactionType.Income -> R.string.app_incomes
    }
}

private fun getValueBoxPrefix(type: TransactionType): Int {
    return when (type) {
        TransactionType.Expense -> R.string.app_minus
        TransactionType.Income -> R.string.app_plus
    }
}