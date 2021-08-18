package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppDialogCustom
import com.emikhalets.myfinances.ui.base.AppTextWithIcon
import com.emikhalets.myfinances.utils.enums.AppIcon

@Composable
fun ChooseWalletDialog(
    wallets: List<Wallet>,
    onSelect: (Wallet) -> Unit,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AppDialogCustom(onDismiss = { onDismiss() }) {
        if (wallets.isEmpty()) AppTextWithIcon(
            text = stringResource(R.string.new_wallet),
            icon = AppIcon.Plus.icon,
            onClick = { onAddClick() }
        )
        else {
            LazyColumn(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                items(wallets) { wallet ->
                    AppTextWithIcon(
                        text = wallet.name,
                        iconPainter = painterResource(R.drawable.ic_wallet),
                        onClick = { onSelect(wallet) }
                    )
                    if (wallet == wallets.last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        AppTextWithIcon(
                            text = stringResource(R.string.new_wallet),
                            icon = AppIcon.Plus.icon,
                            onClick = { onAddClick() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseCategoryDialog(
    categories: List<Category>,
    onSelect: (Category) -> Unit,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AppDialogCustom(onDismiss = { onDismiss() }) {
        if (categories.isEmpty()) AppTextWithIcon(
            text = stringResource(R.string.new_category),
            icon = AppIcon.Plus.icon,
            onClick = { onAddClick() }
        )
        else {
            LazyColumn(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                items(categories) { category ->
                    AppTextWithIcon(
                        text = category.name,
                        iconPainter = painterResource(AppIcon.get(category.icon).icon),
                        onClick = { onSelect(category) }
                    )
                    if (category == categories.last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        AppTextWithIcon(
                            text = stringResource(R.string.new_category),
                            icon = AppIcon.Plus.icon,
                            onClick = { onAddClick() }
                        )
                    }
                }
            }
        }
    }
}