package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseWalletDialog
import com.emikhalets.myfinances.utils.AnimateFadeInOut
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.formatValue
import com.emikhalets.myfinances.utils.toDate
import com.emikhalets.myfinances.utils.toast

@Composable
fun TransactionDetailsScreen(
    navController: NavHostController,
    transactionId: Long,
    viewModel: TransactionDetailsVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var wallet by remember { mutableStateOf<Wallet?>(null) }
    var category by remember { mutableStateOf<Category?>(null) }
    var type by remember { mutableStateOf(TransactionType.None) }

    var walletError by remember { mutableStateOf(false) }
    var categoryError by remember { mutableStateOf(false) }
    var valueError by remember { mutableStateOf(false) }

    var showChoosingCategory by remember { mutableStateOf(false) }
    var showChoosingWallet by remember { mutableStateOf(false) }
    var showAddingCategory by remember { mutableStateOf(false) }
    var showAddingWallet by remember { mutableStateOf(false) }

    LaunchedEffect("init") {
        viewModel.getTransaction(transactionId)
    }
    LaunchedEffect(state) {
        if (state.transaction != null && state.categories.isEmpty()) {
            type = TransactionType.get(state.transaction.type)
            value = state.transaction.amount.toString()
            viewModel.getCategories(state.transaction.type)
        }
        if (state.transaction != null && state.wallets.isEmpty()) {
            viewModel.getWallets()
        }
        if (state.transaction != null && state.categories.isNotEmpty()) {
            category = state.categories.find { it.categoryId == state.transaction.categoryId }
        }
        if (state.transaction != null && state.wallets.isNotEmpty()) {
            wallet = state.wallets.find { it.walletId == state.transaction.walletId }
        }
        if (state.error != null) toast(context, state.errorMessage())
        if (state.deletedTransaction) navController.popBackStack()
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.transaction_details)
    ) {
        Column {
            TransactionTypeChooser(type = type, onSelectType = { type = it })
            TextCenter(text = state.transaction?.timestamp.toDate())
            WalletChooserTextField(
                wallet = wallet,
                error = walletError,
                onClick = {
                    showChoosingWallet = true
                    walletError = false
                }
            )
            CategoryChooserTextField(
                category = category,
                error = categoryError,
                onClick = {
                    showChoosingCategory = true
                    categoryError = false
                }
            )
            NoteTextField(
                note = note,
                onNoteChange = { note = it }
            )
            ValueTextField(
                value = value,
                error = valueError,
                onValueChange = {
                    value = it.formatValue()
                    valueError = false
                }
            )
            Spacer(Modifier.height(16.dp))
            ControlButtons(
                viewModel = viewModel,
                state = state,
                wallet = wallet,
                category = category,
                type = type,
                note = note,
                value = value,
                valueError = valueError,
                walletErrorChange = { walletError = it },
                categoryErrorChange = { categoryError = it },
                valueErrorChange = { valueError = it }
            )
        }
        AnimateFadeInOut(visible = showChoosingWallet, duration = 300) {
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
        AnimateFadeInOut(visible = showAddingWallet, duration = 300) {
            AddWalletDialog(
                onSave = { name, value ->
                    showAddingWallet = false
                    viewModel.saveWallet(name, value)
                },
                onDismiss = { showAddingWallet = false }
            )
        }
        AnimateFadeInOut(visible = showChoosingCategory, duration = 300) {
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
        AnimateFadeInOut(visible = showAddingCategory, duration = 300) {
            AddCategoryDialog(
                onSave = { name, icon ->
                    showAddingCategory = false
                    viewModel.saveCategory(type, name, icon)
                },
                onDismiss = { showAddingCategory = false }
            )
        }
    }
}