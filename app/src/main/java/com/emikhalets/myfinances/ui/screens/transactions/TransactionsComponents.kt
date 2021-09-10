package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.utils.AnimateExpandCollapse
import com.emikhalets.myfinances.utils.enums.MyIcons
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.getMonths
import com.emikhalets.myfinances.utils.navigation.navigateToNewTransaction
import com.emikhalets.myfinances.utils.navigation.navigateToTransactionDetails
import com.emikhalets.myfinances.utils.toDate

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>
) {
    if (list.isEmpty()) {
        TextFullScreen(stringResource(R.string.empty_transactions))
    } else {
        AppVerticalList(list) { transaction ->
            TransactionsListItem(
                transaction = transaction,
                onClick = { navController.navigateToTransactionDetails(it) }
            )
        }
    }
}

@Composable
fun TransactionsListItem(
    transaction: Transaction,
    onClick: (Long) -> Unit
) {
    var showNote by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { showNote = !showNote }
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AppIcon(
                icon = 1,
                size = 32.dp
            )
            Spacer(Modifier.width(24.dp))
            Column {
                AppText("")
                TextSecondary(transaction.timestamp.toDate())
            }
            TextValue(transaction.amount)
        }
        AnimateExpandCollapse(visible = showNote, duration = 200) {
            Column(Modifier.fillMaxWidth()) {
                if (transaction.note.isNotEmpty()) {
                    AppText(
                        text = transaction.note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 52.dp, end = 32.dp)
                    )
                }
                AppTextButton(
                    text = stringResource(R.string.details),
                    padding = PaddingValues(top = 4.dp, bottom = 4.dp),
                    onClick = { onClick(transaction.transactionId) }
                )
            }
        }
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun AddButtonsLayout(
    navController: NavHostController
) {
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
fun AddTransactionButton(
    icon: Int,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(MaterialTheme.colors.primary, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .background(MaterialTheme.colors.onPrimary, CircleShape)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .background(MaterialTheme.colors.primary, CircleShape)
            ) {
                AppIcon(
                    icon = icon,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun DateChooser(
    month: Int,
    year: Int,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit
) {
    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DateChooserTextField(
                value = getMonths()[month],
                label = stringResource(R.string.month),
                onClick = {
                    monthExpanded = !monthExpanded
                }
            )
            DropdownMenu(
                expanded = monthExpanded,
                onDismissRequest = { monthExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                getMonths().forEachIndexed { i, value ->
                    AppText(
                        text = value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                onMonthChange(i)
                                monthExpanded = false
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DateChooserTextField(
                value = year.toString(),
                label = stringResource(R.string.year),
                onClick = {
                    yearExpanded = !yearExpanded
                }
            )
            DropdownMenu(
                expanded = yearExpanded,
                onDismissRequest = { yearExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                (2001..2023).forEach { year ->
                    AppText(
                        text = year.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                onYearChange(year)
                                yearExpanded = false
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}