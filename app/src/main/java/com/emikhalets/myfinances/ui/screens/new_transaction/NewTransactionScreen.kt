package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseWalletDialog
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getLabel
import com.emikhalets.myfinances.utils.navigation.navigateBack

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    val state = viewModel.state
    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var wallet by remember { mutableStateOf<Wallet?>(null) }
    var category by remember { mutableStateOf<Category?>(null) }
    var showChoosingCategory by remember { mutableStateOf(false) }
    var showChoosingWallet by remember { mutableStateOf(false) }
    var showAddingCategory by remember { mutableStateOf(false) }
    var showAddingWallet by remember { mutableStateOf(false) }

    LaunchedEffect("init_key") {
        viewModel.getCategories(transactionType)
        viewModel.getWallets()
    }

    ScreenScaffold(
        navController = navController,
        title = transactionType.getLabel()
    ) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                ButtonChooser(
                    label = stringResource(R.string.wallet),
                    name = wallet?.name ?: stringResource(R.string.empty_wallets),
                    icon = R.drawable.ic_wallet,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showChoosingWallet = true }
                )
                ButtonChooser(
                    label = stringResource(R.string.category),
                    name = category?.name ?: stringResource(R.string.empty_categories),
                    icon = R.drawable.ic_coins,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showChoosingCategory = true }
                )
            }
            AppTextField(
                value = note,
                onValueChange = { note = it },
                label = stringResource(R.string.note)
            )
            AppTextField(
                label = stringResource(R.string.value),
                value = value,
                onValueChange = { value = it },
                type = KeyboardType.Number
            )
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { navController.navigateBack() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                TextButton(
                    onClick = {
                        viewModel.saveTransaction(
                            wallet?.walletId,
                            category?.categoryId,
                            note,
                            value,
                            transactionType
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
        if (showChoosingWallet) {
            ChooseWalletDialog(
                wallets = state.wallets,
                onSelect = {
                    wallet = it
                    showChoosingWallet = false
                },
                onDismiss = { showChoosingWallet = false },
                onAddClick = {
                    showChoosingWallet = false
                    showAddingWallet = true
                }
            )
        }
        if (showAddingWallet) {
            AddWalletDialog(
                onSave = { name, value ->
                    showAddingWallet = false
                    viewModel.saveWallet(name, value)
                },
                onDismiss = { showAddingWallet = false }
            )
        }
        if (showChoosingCategory) {
            ChooseCategoryDialog(
                categories = state.categories,
                onSelect = {
                    category = it
                    showChoosingCategory = false
                },
                onDismiss = { showChoosingCategory = false },
                onAddClick = {
                    showChoosingCategory = false
                    showAddingCategory = true
                }
            )
        }
        if (showAddingCategory) {
            AddCategoryDialog(
                onSave = {
                    showAddingCategory = false
                    viewModel.saveCategory(transactionType, it)
                },
                onDismiss = { showAddingCategory = false }
            )
        }
    }
}

@Composable
fun ButtonChooser(
    label: String,
    name: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}