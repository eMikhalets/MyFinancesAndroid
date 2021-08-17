package com.emikhalets.myfinances.ui.screens.new_transaction

import android.util.Log
import androidx.compose.foundation.layout.*
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
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.screens.dialogs.AddCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.AddWalletDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseCategoryDialog
import com.emikhalets.myfinances.ui.screens.dialogs.ChooseWalletDialog
import com.emikhalets.myfinances.utils.CurrencyTransformation
import com.emikhalets.myfinances.utils.animations.AnimateFadeInOut
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getLabel
import com.emikhalets.myfinances.utils.formatValue
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

    var walletError by remember { mutableStateOf(false) }
    var categoryError by remember { mutableStateOf(false) }
    var valueError by remember { mutableStateOf(false) }

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
                    showChoosingWallet = true
                    walletError = false
                },
                errorSelecting = walletError
            )
            AppTextField(
                value = category?.name ?: stringResource(R.string.choose_category),
                onValueChange = {},
                label = stringResource(R.string.category),
                leadingIcon = R.drawable.ic_coins,
                trailingIcon = R.drawable.ic_keyboard_arrow_down,
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
                leadingIcon = R.drawable.ic_edit
            )
            AppTextField(
                label = stringResource(R.string.value),
                value = value,
                onValueChange = {
                    Log.d("TAG", "it = $it")
                    value = it.formatValue()
                    Log.d("TAG", "value = $value")
                    valueError = false
                },
                leadingIcon = R.drawable.ic_attach_money,
                type = KeyboardType.Number,
                visualTransformation = CurrencyTransformation(),
                errorInvalid = valueError
            )
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                AppTextButton(
                    text = stringResource(R.string.cancel),
                    onClick = { navController.navigateBack() },
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
                                wallet!!.walletId,
                                category!!.categoryId,
                                note,
                                amount,
                                transactionType
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
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