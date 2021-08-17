package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.AppTextFillScreen
import com.emikhalets.myfinances.ui.base.AppVerticalList
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(transaction.transactionId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_coins),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column() {
            Text(text = transaction.categoryName)
            if (transaction.note.isNotEmpty()) {
                Spacer(Modifier.width(8.dp))
                Text(text = transaction.note)
            }
        }
        Text(
            text = transaction.amount.toValue(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
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