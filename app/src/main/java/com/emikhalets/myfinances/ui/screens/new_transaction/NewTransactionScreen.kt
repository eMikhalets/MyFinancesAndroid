package com.emikhalets.myfinances.ui.screens.new_transaction

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.emikhalets.myfinances.utils.animations.AnimateFadeInOut
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

    LaunchedEffect(state) {
        if (state.categories.isNotEmpty() && category == null) {
            category = state.categories.first()
        }
        if (state.wallets.isNotEmpty() && wallet == null) {
            wallet = state.wallets.first()
        }
    }

    ScreenScaffold(
        navController = navController,
        title = transactionType.getLabel()
    ) {
        Column {
            AppTextField(
                value = wallet?.name ?: stringResource(R.string.choose_wallet),
                onValueChange = {},
                label = stringResource(R.string.wallet),
                leadingIcon = R.drawable.ic_wallet,
                trailingIcon = R.drawable.ic_keyboard_arrow_down,
                enabled = false,
                onClick = {
                    Log.d("TAG", "NewTransactionScreen")
                    showChoosingWallet = true
                }
            )
            AppTextField(
                value = category?.name ?: stringResource(R.string.choose_category),
                onValueChange = {},
                label = stringResource(R.string.category),
                leadingIcon = R.drawable.ic_coins,
                trailingIcon = R.drawable.ic_keyboard_arrow_down,
                enabled = false,
                onClick = { showChoosingCategory = true }
            )
            AppTextField(
                value = note,
                onValueChange = { note = it },
                label = stringResource(R.string.note),
                leadingIcon = R.drawable.ic_edit
            )
            AppTextField(
                label = stringResource(R.string.value),
                value = value,
                onValueChange = { value = it },
                leadingIcon = R.drawable.ic_attach_money,
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
        AnimateFadeInOut(visible = showChoosingWallet, duration = 200) {
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
        AnimateFadeInOut(visible = showAddingWallet, duration = 200) {
            AddWalletDialog(
                onSave = { name, value ->
                    showAddingWallet = false
                    viewModel.saveWallet(name, value)
                },
                onDismiss = { showAddingWallet = false }
            )
        }
        AnimateFadeInOut(visible = showChoosingCategory, duration = 200) {
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
        AnimateFadeInOut(visible = showAddingCategory, duration = 200) {
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