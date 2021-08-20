package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseWalletDialog
import com.emikhalets.myfinances.utils.*
import com.emikhalets.myfinances.utils.enums.AppIcon
import com.emikhalets.myfinances.utils.enums.TransactionType

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

    LaunchedEffect("init_key") {
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
    }

    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.transaction_details)
    ) {
        Column {
            TransactionTypeChooser(
                type = type,
                onSelectType = { type = it }
            )
            Text(
                text = state.transaction?.timestamp.toDate(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            AppTextField(
                value = wallet?.name ?: stringResource(R.string.choose_wallet),
                onValueChange = {},
                label = stringResource(R.string.wallet),
                leadingIcon = R.drawable.ic_wallet,
                trailingIcon = AppIcon.ArrowDown.icon,
                padding = PaddingValues(start = 16.dp, end = 16.dp),
                enabled = false,
                onClick = {
                    showChoosingWallet = true
                    walletError = false
                },
                errorSelecting = walletError
            )
            AppTextField(
                value = category?.name ?: stringResource(R.string.choose_category),
                onValueChange = {},
                label = stringResource(R.string.category),
                leadingIcon = AppIcon.get(category?.icon ?: 3).icon,
                trailingIcon = AppIcon.ArrowDown.icon,
                enabled = false,
                onClick = {
                    showChoosingCategory = true
                    categoryError = false
                },
                errorSelecting = categoryError
            )
            AppTextField(
                value = note,
                onValueChange = { note = it },
                label = stringResource(R.string.note),
                leadingIcon = AppIcon.Pencil.icon
            )
            AppTextField(
                label = stringResource(R.string.value),
                value = value,
                onValueChange = {
                    value = it.formatValue()
                    valueError = false
                },
                leadingIcon = AppIcon.Money.icon,
                type = KeyboardType.Number,
                visualTransformation = CurrencyTransformation(),
                errorInvalid = valueError
            )
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                AppTextButton(
                    text = stringResource(R.string.cancel),
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                )
                AppTextButton(
                    text = stringResource(R.string.save),
                    onClick = {
                        val amount = try {
                            value.toDouble()
                        } catch (ex: NumberFormatException) {
                            ex.printStackTrace()
                            valueError = true
                            0.0
                        }
                        when {
                            wallet == null -> walletError = true
                            category == null -> categoryError = true
                            valueError -> valueError = true
                            else -> viewModel.saveTransaction(
                                wallet = wallet!!,
                                category = category!!,
                                amount = amount,
                                type = type.value,
                                note = note,
                                date = state.transaction?.timestamp ?: 0
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
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