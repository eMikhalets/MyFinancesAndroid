package com.emikhalets.presentation.core

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun TransactionTypeChooser(
    selectedType: TransactionType,
    onTypeSelect: (TransactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            text = "stringResource(R.string.expenses)",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Expense) }
                .borderTypeExpense(selectedType)
                .padding(8.dp)
        )
        Text(
            text = "stringResource(R.string.incomes)",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Income) }
                .borderTypeIncome(selectedType)
                .padding(8.dp)
        )
    }
}

@Composable
private fun Modifier.borderTypeExpense(type: TransactionType) = when (type) {
    TransactionType.Expense -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Composable
private fun Modifier.borderTypeIncome(type: TransactionType) = when (type) {
    TransactionType.Income -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Preview(showBackground = true)
@Composable
private fun TransactionTypeChooserPreview() {
    AppTheme {
        Box(Modifier.padding(8.dp)) {
            TransactionTypeChooser(
                selectedType = TransactionType.Expense,
                onTypeSelect = {}
            )
        }
    }
}
