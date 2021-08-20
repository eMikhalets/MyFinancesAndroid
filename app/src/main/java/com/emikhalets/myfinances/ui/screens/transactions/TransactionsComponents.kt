package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.TextFullScreen
import com.emikhalets.myfinances.ui.base.AppVerticalList
import com.emikhalets.myfinances.utils.AnimateExpandCollapse
import com.emikhalets.myfinances.utils.navigation.navigateToTransactionDetails
import com.emikhalets.myfinances.utils.toDate
import com.emikhalets.myfinances.utils.toValue

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>
) {
    if (list.isEmpty()) TextFullScreen(
        text = stringResource(R.string.empty_transactions)
    )
    else AppVerticalList(list) { transaction ->
        TransactionsListItem(
            transaction = transaction,
            onClick = { navController.navigateToTransactionDetails(it) }
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
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_coins),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(24.dp))
            Column {
                Text(
                    text = transaction.categoryName,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = transaction.timestamp.toDate(),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.secondary
                    )
                )
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
        AnimateExpandCollapse(visible = showNote, duration = 300) {
            Column(Modifier.fillMaxWidth()) {
                if (transaction.note.isNotEmpty()) Text(
                    text = transaction.note,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 52.dp, end = 32.dp)
                )
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
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }
        }
    }
}