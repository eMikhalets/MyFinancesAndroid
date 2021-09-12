package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppText
import com.emikhalets.myfinances.ui.base.ScreenScaffold
import com.emikhalets.myfinances.ui.base.ValueTextField
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.enums.Keyboard
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.formatValue

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

//    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
//    var wallet by remember { mutableStateOf<Wallet?>(null) }
//    var category by remember { mutableStateOf<Category?>(null) }

//    var walletError by remember { mutableStateOf(false) }
//    var categoryError by remember { mutableStateOf(false) }
//    var valueError by remember { mutableStateOf(false) }

//    var showChoosingCategory by remember { mutableStateOf(false) }
//    var showChoosingWallet by remember { mutableStateOf(false) }
//    var showAddingCategory by remember { mutableStateOf(false) }
//    var showAddingWallet by remember { mutableStateOf(false) }
//    var categoryAfterAdding by remember { mutableStateOf("") }

//    LaunchedEffect("init") {
//        viewModel.getCategories(transactionType)
//        viewModel.getWallets()
//    }
//    LaunchedEffect(state) {
//        if (state.wallets.isNotEmpty() && wallet == null) {
//            wallet = state.wallets.find { it.walletId == context.getCurrentWalletId() }
//        }
//        if (state.savedTransaction) navController.popBackStack()
//        if (state.savedWallet) viewModel.getWallets()
//        if (state.savedCategory) viewModel.getCategories(transactionType)
//        if (state.error != null) toast(context, state.error)
//        if (categoryAfterAdding.isNotEmpty()) {
//            category = state.categories.find { it.name == categoryAfterAdding }
//            if (category != null) categoryAfterAdding = ""
//        }
//    }

    NewTransactionScreen(
        navController = navController,
        transactionType = transactionType,
        value = value,
        onValueChange = {
            value = it.formatValue()
        }
    )

//    ScreenScaffold(
//        navController = navController,
//        title = transactionType.getLabel()
//    ) {
//        Column {
//            WalletChooserTextField(
//                wallet = wallet,
//                error = walletError,
//                onClick = {
//                    showChoosingWallet = true
//                    walletError = false
//                }
//            )
//            CategoryChooserTextField(
//                category = category,
//                error = categoryError,
//                onClick = {
//                    showChoosingCategory = true
//                    categoryError = false
//                }
//            )
//            NoteTextField(
//                note = note,
//                onNoteChange = { note = it }
//            )
//            ValueTextField(
//                value = value,
//                error = valueError,
//                onValueChange = {
//                    value = it.formatValue()
//                    valueError = false
//                }
//            )
//            Spacer(Modifier.height(16.dp))
//            Row(Modifier.fillMaxWidth()) {
//                AppTextButton(
//                    text = stringResource(R.string.cancel),
//                    onClick = { navController.popBackStack() },
//                    modifier = Modifier.weight(1f)
//                )
//                AppTextButton(
//                    text = stringResource(R.string.save),
//                    onClick = {
//                        val amount = try {
//                            value.toDouble()
//                        } catch (ex: NumberFormatException) {
//                            ex.printStackTrace()
//                            valueError = true
//                            0.0
//                        }
//                        when {
//                            wallet == null -> walletError = true
//                            category == null -> categoryError = true
//                            valueError -> valueError = true
//                            else -> viewModel.saveTransaction(
//                                wallet!!,
//                                category!!,
//                                note,
//                                amount,
//                                transactionType
//                            )
//                        }
//                    },
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//        AnimateFadeInOut(visible = showChoosingWallet, duration = 300) {
//            ListChooserDialog(
//                buttonText = stringResource(R.string.new_wallet),
//                items = state.wallets,
//                onDismiss = { showChoosingWallet = false },
//                onSelect = {
//                    wallet = it
//                    showChoosingWallet = false
//                },
//                onAddClick = {
//                    showChoosingWallet = false
//                    showAddingWallet = true
//                }
//            )
//        }
//        AnimateFadeInOut(visible = showAddingWallet, duration = 300) {
//            AddWalletDialog(
//                onSave = { name, value ->
//                    showAddingWallet = false
//                    viewModel.saveWallet(name, value)
//                },
//                onDismiss = { showAddingWallet = false }
//            )
//        }
//        AnimateFadeInOut(visible = showChoosingCategory, duration = 300) {
//            ListChooserDialog(
//                buttonText = stringResource(R.string.new_category),
//                items = state.categories,
//                onDismiss = { showChoosingCategory = false },
//                onSelect = {
//                    category = it
//                    showChoosingCategory = false
//                },
//                onAddClick = {
//                    showChoosingCategory = false
//                    showAddingCategory = true
//                }
//            )
//        }
//        AnimateFadeInOut(visible = showAddingCategory, duration = 300) {
//            AddCategoryDialog(
//                onSave = { name, _ ->
//                    showAddingCategory = false
//                    categoryAfterAdding = name
//                    viewModel.saveCategory(transactionType, name)
//                },
//                onDismiss = { showAddingCategory = false }
//            )
//        }
//    }
}

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    value: String,
    onValueChange: (String) -> Unit
) {
    var valueError by remember { mutableStateOf(false) }

    val title = when (transactionType) {
        TransactionType.Expense -> stringResource(R.string.new_expense)
        TransactionType.Income -> stringResource(R.string.new_income)
        TransactionType.None -> "-"
    }

    ScreenScaffold(
        navController = navController,
        title = title
    ) {
        Column(Modifier.fillMaxSize()) {
            ValueTextField(
                value = value,
                error = valueError,
                onValueChange = {
                    onValueChange(value)
                    valueError = false
                }
            )
            Spacer(Modifier.height(8.dp))
            Keyboard(onClick = {})
        }
    }
}

@Composable
fun Keyboard(onClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(value = Keyboard.NUM_1.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_2.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_3.value, onClick = onClick)
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(value = Keyboard.NUM_4.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_5.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_6.value, onClick = onClick)
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(value = Keyboard.NUM_7.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_8.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_9.value, onClick = onClick)
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(value = Keyboard.DOT.value, onClick = onClick)
            KeyboardButton(value = Keyboard.NUM_0.value, onClick = onClick)
            KeyboardButton(value = Keyboard.DEL.value, onClick = onClick)
        }
    }
}

@Composable
fun KeyboardButton(value: String, onClick: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colors.secondary.copy(alpha = 0.3f))
            .clickable { onClick(value) }
            .size(80.dp, 50.dp)
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
    ) {
        AppText(
            text = value,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewTransactionScreenPreview() {
    MyFinancesTheme {
        NewTransactionScreen(
            navController = rememberNavController(),
            transactionType = TransactionType.Expense,
            value = "50.00",
            onValueChange = {}
        )
    }
}