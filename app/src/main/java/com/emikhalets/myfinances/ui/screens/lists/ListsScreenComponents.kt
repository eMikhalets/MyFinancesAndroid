package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.utils.enums.MyIcons
import com.emikhalets.myfinances.utils.getCurrentWalletId
import com.emikhalets.myfinances.utils.setCurrentWalletId

@Composable
fun WalletsList(
    list: List<Wallet>,
    onAddClick: () -> Unit
) {
    val context = LocalContext.current
    var current by remember { mutableStateOf(context.getCurrentWalletId()) }

    LaunchedEffect("init") {
        if (list.any { it.walletId == current }) {
            current = 0
            context.setCurrentWalletId(0)
        }
    }

    Column {
        TextWithIcon(
            text = stringResource(R.string.new_wallet),
            icon = MyIcons.Plus.icon,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.secondary)
        if (list.isEmpty()) {
            TextFullScreen(stringResource(R.string.empty_wallets))
        } else {
            AppVerticalList(list) { wallet ->
                WalletListItem(
                    wallet = wallet,
                    current = current,
                    onClick = {
                        context.setCurrentWalletId(wallet.walletId)
                        current = wallet.walletId
                    }
                )
            }
        }
    }
}

@Composable
fun WalletListItem(
    wallet: Wallet,
    current: Long,
    onClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(wallet.walletId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppIcon(
            icon = MyIcons.Wallet.icon,
            size = 40.dp
        )
        Spacer(Modifier.width(16.dp))
        Column {
            AppText(text = wallet.name)
            if (wallet.walletId == current) {
                Spacer(Modifier.width(8.dp))
                AppText(
                    text = stringResource(R.string.by_default),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        TextValue(value = wallet.amount)
    }
}

@Composable
fun CategoriesList(
    navController: NavHostController,
    list: List<Category>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TextWithIcon(
            text = stringResource(R.string.new_category),
            icon = MyIcons.Plus.icon,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.secondary)
        if (list.isEmpty()) {
            TextFullScreen(stringResource(R.string.empty_categories))
        } else {
            AppVerticalList(list) { category ->
                CategoryListItem(
                    category = category,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun CategoryListItem(
    category: Category,
    onClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(category.categoryId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppIcon(
            icon = 1,
            size = 40.dp
        )
        Spacer(Modifier.width(16.dp))
        AppText(category.name)
    }
}