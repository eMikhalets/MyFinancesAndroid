package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextFillScreen
import com.emikhalets.myfinances.ui.base.AppVerticalList
import com.emikhalets.myfinances.utils.AnimateExpandCollapse
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigation.navigateToNewTransaction
import com.emikhalets.myfinances.utils.toValue

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>
) {
    if (list.isEmpty()) AppTextFillScreen(
        text = stringResource(R.string.empty_transactions)
    )
    else AppVerticalList(list) { transaction ->
        TransactionsListItem(
            transaction = transaction,
            onClick = {}
        )
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
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_coins),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = transaction.categoryName,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = transaction.amount.toValue(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
        }
        AnimateExpandCollapse(visible = showNote, duration = 300) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = transaction.note,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 52.dp, end = 32.dp)
                )
                Spacer(Modifier.height(8.dp))
                AppTextButton(
                    text = stringResource(R.string.details),
                    onClick = { onClick(transaction.transactionId) }
                )
            }
        }
        Divider(color = MaterialTheme.colors.secondary)
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