package com.emikhalets.myfinances.presentation.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
import com.emikhalets.myfinances.presentation.core.AppScaffold
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toDate

@Composable
fun IncomesScreen(
    navController: NavHostController,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getIncomeTransactions()
    }

    AppScaffold(navController) {
        IncomesScreen(
            entities = state.incomes,
            onTransactionClick = {
                // TODO: open transaction edit screen
            },
            onDeleteClick = {
                // TODO: delete transaction from db
            },
        )
    }
}

@Composable
private fun IncomesScreen(
    entities: List<TransactionEntity>,
    onTransactionClick: (Transaction) -> Unit,
    onDeleteClick: (Transaction) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(entities) { entity ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onTransactionClick(entity.transaction) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = entity.category.name,
                                fontSize = 18.sp
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = entity.transaction.timestamp.toDate(),
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = entity.transaction.note,
                            fontSize = 14.sp)
                    }
                    Text(
                        text = entity.transaction.value.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Divider(color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppScaffold(rememberNavController()) {
            IncomesScreen(
                entities = listOf(
                    TransactionEntity(
                        Transaction(
                            value = 450.00,
                            categoryId = 0,
                            note = "some description",
                            type = TransactionType.Income
                        ),
                        Category(
                            name = "Category",
                            type = TransactionType.Income
                        )
                    ),
                    TransactionEntity(
                        Transaction(
                            value = 102458.99,
                            categoryId = 0,
                            note = "some description",
                            type = TransactionType.Income
                        ),
                        Category(
                            name = "Category",
                            type = TransactionType.Income
                        )
                    ),
                    TransactionEntity(
                        Transaction(
                            value = 450.02,
                            categoryId = 0,
                            note = "some description",
                            type = TransactionType.Income
                        ),
                        Category(
                            name = "Category",
                            type = TransactionType.Income
                        )
                    ),
                    TransactionEntity(
                        Transaction(
                            value = 450.10,
                            categoryId = 0,
                            note = "some description",
                            type = TransactionType.Income
                        ),
                        Category(
                            name = "Category",
                            type = TransactionType.Income
                        )
                    )
                ),
                onTransactionClick = {},
                onDeleteClick = {},
            )
        }
    }
}