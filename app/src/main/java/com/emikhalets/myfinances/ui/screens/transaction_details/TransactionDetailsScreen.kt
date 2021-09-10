package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ListChooserDialog
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AnimateFadeInOut
import com.emikhalets.myfinances.utils.enums.MyIcons
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
        if (state.error != null) toast(context, state.error)
        if (state.deletedTransaction) navController.popBackStack()
    }

    TransactionDetailsScreen(
        navController = navController,
        viewModel = viewModel,
        wallets = state.wallets,
        categories = state.categories,
        date = state.transaction?.timestamp,
        note = note,
        value = value,
        wallet = wallet,
        category = category,
        type = type,
        walletError = walletError,
        categoryError = categoryError,
        valueError = valueError,
        onNoteChange = { note = it },
        onValueChange = { value = it },
        onWalletChange = { wallet = it },
        onCategoryChange = { category = it },
        onTypeChange = { type = it },
        onWalletErrorChange = { walletError = it },
        onCategoryErrorChange = { categoryError = it },
        onValueErrorChange = { valueError = it }
    )
}

@Composable
fun TransactionDetailsScreen(
    navController: NavHostController,
    viewModel: TransactionDetailsVM,
    wallets: List<Wallet>,
    categories: List<Category>,
    date: Long?,
    note: String,
    value: String,
    wallet: Wallet?,
    category: Category?,
    type: TransactionType,
    walletError: Boolean,
    categoryError: Boolean,
    valueError: Boolean,
    onNoteChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onWalletChange: (Wallet) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onWalletErrorChange: (Boolean) -> Unit,
    onCategoryErrorChange: (Boolean) -> Unit,
    onValueErrorChange: (Boolean) -> Unit
) {
    var showChoosingCategory by remember { mutableStateOf(false) }
    var showChoosingWallet by remember { mutableStateOf(false) }
    var showAddingCategory by remember { mutableStateOf(false) }
    var showAddingWallet by remember { mutableStateOf(false) }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.transaction_details),
        icon = MyIcons.ArrowBack.icon
    ) {
        Column {
            TransactionTypeChooser(
                type = type,
                onSelectType = onTypeChange
            )
            TextCenter(date.toDate())
            WalletChooserTextField(
                wallet = wallet,
                error = walletError,
                onClick = {
                    showChoosingWallet = true
                    onWalletErrorChange(false)
                }
            )
            CategoryChooserTextField(
                category = category,
                error = categoryError,
                onClick = {
                    showChoosingCategory = true
                    onCategoryErrorChange(false)
                }
            )
            NoteTextField(
                note = note,
                onNoteChange = onNoteChange
            )
            ValueTextField(
                value = value,
                error = valueError,
                onValueChange = {
                    onValueChange(it.formatValue())
                    onValueErrorChange(false)
                }
            )
            Spacer(Modifier.height(16.dp))
            ControlButtons(
                viewModel = viewModel,
                date = date,
                wallet = wallet,
                category = category,
                type = type,
                note = note,
                value = value,
                valueError = valueError,
                walletErrorChange = onWalletErrorChange,
                categoryErrorChange = onCategoryErrorChange,
                valueErrorChange = onValueErrorChange
            )
        }
        AnimateFadeInOut(visible = showChoosingWallet, duration = 300) {
            ListChooserDialog(
                buttonText = stringResource(R.string.new_wallet),
                items = wallets,
                onDismiss = { showChoosingWallet = false },
                onSelect = {
                    onWalletChange(it)
                    showChoosingWallet = false
                },
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
            ListChooserDialog(
                buttonText = stringResource(R.string.new_category),
                items = categories,
                onDismiss = { showChoosingCategory = false },
                onSelect = {
                    onCategoryChange(it)
                    showChoosingCategory = false
                },
                onAddClick = {
                    showChoosingCategory = false
                    showAddingCategory = true
                }
            )
        }
        AnimateFadeInOut(visible = showAddingCategory, duration = 300) {
            AddCategoryDialog(
                onSave = { name, _ ->
                    showAddingCategory = false
                    viewModel.saveCategory(type, name)
                },
                onDismiss = { showAddingCategory = false }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsPreview() {
    MyFinancesTheme {
        TransactionDetailsScreen(
            navController = rememberNavController(),
            viewModel = hiltViewModel(),
            wallets = emptyList(),
            categories = emptyList(),
            date = 0,
            note = "Some note",
            value = "250.54",
            wallet = null,
            category = null,
            type = TransactionType.Income,
            walletError = false,
            categoryError = false,
            valueError = false,
            onNoteChange = {},
            onValueChange = {},
            onWalletChange = {},
            onCategoryChange = {},
            onTypeChange = {},
            onWalletErrorChange = {},
            onCategoryErrorChange = {},
            onValueErrorChange = {}
        )
    }
}