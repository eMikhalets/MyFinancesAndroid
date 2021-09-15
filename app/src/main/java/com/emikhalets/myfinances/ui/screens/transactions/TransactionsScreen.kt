package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionWithCategory
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.*
import com.emikhalets.myfinances.utils.enums.MyIcons
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigation.navigateToNewTransaction
import com.emikhalets.myfinances.utils.navigation.navigateToTransactionDetails
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
    var currentWalletId by remember { mutableStateOf(0L) }

    LaunchedEffect("init") {
        currentWalletId = SharedPrefs.getCurrentWalletId(context)
        viewModel.getTransactions(month, year, currentWalletId)
    }
    LaunchedEffect(state) {
        if (state.error != null) toast(context, state.error)
    }

    TransactionsScreen(
        navController = navController,
        transactions = state.transactions,
        month = month,
        year = year,
        onBackDateClick = {
            val new = Calendar.getInstance()
            new.set(Calendar.MONTH, month)
            new.add(Calendar.MONTH, -1)
            month = new.month()
            if (month == 11) year--
            viewModel.getTransactions(month, year, currentWalletId)
        },
        onForwardDateClick = {
            val new = Calendar.getInstance()
            new.set(Calendar.MONTH, month)
            new.add(Calendar.MONTH, 1)
            month = new.month()
            if (month == 0) year++
            viewModel.getTransactions(month, year, currentWalletId)
        },
    )
}

@Composable
fun TransactionsScreen(
    navController: NavHostController,
    transactions: List<TransactionWithCategory>,
    month: Int,
    year: Int,
    onBackDateClick: () -> Unit,
    onForwardDateClick: () -> Unit
) {
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
            if (transactions.isEmpty()) {
                TextFullScreen(
                    text = stringResource(R.string.empty_transactions),
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(transactions) { transaction ->
                        TransactionsItem(
                            transaction = transaction,
                            onClick = { navController.navigateToTransactionDetails(it) }
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            AddButtonsLayout(navController)
        }
    }
}

@Composable
fun TransactionsItem(transaction: TransactionWithCategory, onClick: (Long) -> Unit) {
    var showNote by remember { mutableStateOf(false) }
    val color = when (TransactionType.get(transaction.transaction.type)) {
        TransactionType.Expense -> Color.Red.copy(alpha = 0.1f)
        TransactionType.Income -> Color.Green.copy(alpha = 0.1f)
        TransactionType.None -> Color.Transparent
    }

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showNote = !showNote }
                .background(color)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            AppText(
                text = transaction.category.name,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            AppTextMoney(
                value = transaction.transaction.value,
                type = TransactionType.get(transaction.transaction.type)
            )
        }
        AnimateExpandCollapse(visible = showNote, duration = 200) {
            Column(Modifier.fillMaxWidth()) {
                if (transaction.transaction.note.isNotEmpty()) {
                    AppText(
                        text = transaction.transaction.note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 52.dp, end = 32.dp)
                    )
                }
                AppTextButton(
                    text = stringResource(R.string.details),
                    padding = PaddingValues(top = 4.dp, bottom = 4.dp),
                    onClick = { onClick(transaction.transaction.transactionId) }
                )
            }
        }
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun AddButtonsLayout(navController: NavHostController) {
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
            icon = MyIcons.Minus.icon,
            onClick = { navController.navigateToNewTransaction(TransactionType.Expense) }
        )
        Spacer(modifier = Modifier.width(50.dp))
        AddTransactionButton(
            icon = MyIcons.Plus.icon,
            onClick = { navController.navigateToNewTransaction(TransactionType.Income) }
        )
    }
}

@Composable
fun AddTransactionButton(icon: Int, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(80.dp, 40.dp)
            .background(MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        AppIcon(
            drawable = icon,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        TransactionsScreen(
            navController = rememberNavController(),
            transactions = listOf(
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 53555.43, type = 0),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 535.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 55.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 53555.43, type = 0),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 535.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 55.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 55.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 53555.43, type = 0),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 535.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 55.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 55.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 5355.43, type = 1),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 53555.43, type = 0),
                    category = Category(name = "Category")
                ),
                TransactionWithCategory(
                    transaction = Transaction(value = 535.43, type = 1),
                    category = Category(name = "Category")
                )
            ),
            month = Calendar.getInstance().month(),
            year = Calendar.getInstance().year(),
            onBackDateClick = {},
            onForwardDateClick = {}
        )
    }
}