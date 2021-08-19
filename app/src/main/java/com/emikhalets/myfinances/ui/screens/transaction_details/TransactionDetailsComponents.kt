package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun TransactionTypeChooser(
    type: TransactionType,
    onSelectType: (TransactionType) -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.expense),
            textColor = MaterialTheme.colors.onPrimary,
            onClick = { onSelectType(TransactionType.Expense) },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = if (type == TransactionType.Expense) {
                        MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    } else {
                        Color.Transparent
                    }
                )
        )
        AppTextButton(
            text = stringResource(R.string.income),
            textColor = MaterialTheme.colors.onPrimary,
            onClick = { onSelectType(TransactionType.Income) },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = if (type == TransactionType.Income) {
                        MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    } else {
                        Color.Transparent
                    }
                )
        )
    }
}