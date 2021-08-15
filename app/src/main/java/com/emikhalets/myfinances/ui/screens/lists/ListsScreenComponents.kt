package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppTextFillScreen
import com.emikhalets.myfinances.ui.base.AppTextWithIcon
import com.emikhalets.myfinances.ui.base.AppVerticalList

@Composable
fun WalletsList(
    navController: NavHostController,
    list: List<Wallet>,
    onAddClick: () -> Unit
) {
    Column {
        AppTextWithIcon(
            text = stringResource(R.string.new_wallet),
            icon = Icons.Default.Add,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.primary)
        if (list.isEmpty()) AppTextFillScreen(
            text = stringResource(R.string.empty_wallets)
        )
        else AppVerticalList(list) { wallet ->
            WalletListItem(
                wallet = wallet,
                onClick = {}
            )
        }
    }
}

@Composable
fun WalletListItem(
    wallet: Wallet,
    onClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(wallet.walletId) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_wallet),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(text = wallet.name)
            Spacer(Modifier.width(8.dp))
            Text(text = wallet.amount.toString())
        }
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
        AppTextWithIcon(
            text = stringResource(R.string.new_category),
            icon = Icons.Default.Add,
            onClick = { onAddClick() }
        )
        Divider(color = MaterialTheme.colors.primary)
        if (list.isEmpty()) AppTextFillScreen(
            text = stringResource(R.string.empty_categories)
        )
        else AppVerticalList(list) { category ->
            CategoryListItem(
                category = category,
                onClick = {}
            )
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
        Icon(
            painter = painterResource(R.drawable.ic_coins),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(text = category.name)
    }
}