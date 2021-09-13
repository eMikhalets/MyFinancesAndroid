package com.emikhalets.myfinances.ui.screens.new_transaction

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.ui.base.*
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AnimateExpandCollapse
import com.emikhalets.myfinances.utils.enums.Keyboard
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.formatValue
import com.emikhalets.myfinances.utils.getCurrentWalletId
import com.emikhalets.myfinances.utils.toast

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    viewModel: NewTransactionVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var value by remember { mutableStateOf("0") }
    var note by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect("init") {
        viewModel.getCategories(transactionType)
    }
    LaunchedEffect(state) {
        if (state.savedTransaction) navController.popBackStack()
        if (state.savedCategory) viewModel.getCategories(transactionType)
        if (state.error != null) toast(context, state.error)
    }

    NewTransactionScreen(
        navController = navController,
        transactionType = transactionType,
        value = value,
        onValueChange = { value = (value + it).formatValue() },
        note = note,
        onNoteChange = { note = it },
        category = category,
        categories = state.categories,
        onCategoryClick = { category = it },
        context = context,
        onSaveClick = {
            viewModel.saveTransaction(
                context.getCurrentWalletId(),
                category,
                note,
                value.toDouble(),
                transactionType
            )
        }
    )
}

@Composable
fun NewTransactionScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    value: String,
    onValueChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    category: Category?,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    context: Context,
    onSaveClick: () -> Unit
) {
    val title = when (transactionType) {
        TransactionType.Expense -> stringResource(R.string.new_expense)
        TransactionType.Income -> stringResource(R.string.new_income)
        TransactionType.None -> "-"
    }

    ScreenScaffold(navController = navController, title = title) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ValueField(value)
            Spacer(Modifier.height(16.dp))
            NoteTextField(note = note, onNoteChange = onNoteChange)
            Spacer(Modifier.height(16.dp))
            Keyboard(onClick = onValueChange)
            Spacer(Modifier.height(16.dp))
            CategoriesLayout(categories = categories, onCategoryClick = onCategoryClick)
            Spacer(Modifier.height(16.dp))
            ControlButtons(
                navController = navController,
                context = context,
                category = category,
                onSaveClick = onSaveClick
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ValueField(value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
    ) {
        AppIcon(
            icon = R.drawable.ic_money,
            modifier = Modifier.padding(16.dp)
        )
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp)
                .width(1.dp)
        )
        if (value.isEmpty()) {
            AppText(
                text = "0",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            AppText(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
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
            .clip(RoundedCornerShape(4.dp))
            .size(80.dp, 50.dp)
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
    ) {
        AppText(
            text = value,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CategoriesLayout(categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        AppText(
            text = stringResource(R.string.choose_category),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .clip(RoundedCornerShape(4.dp))
        )
        AnimateExpandCollapse(visible = expanded, duration = 300) {
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            AppVerticalList(list = categories) { category ->
                AppText(
                    text = category.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ControlButtons(
    navController: NavHostController,
    context: Context,
    category: Category?,
    onSaveClick: () -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.cancel),
            onClick = { navController.popBackStack() },
            modifier = Modifier.weight(1f)
        )
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when (category) {
                    null -> toast(context, R.string.error_selecting_category)
                    else -> onSaveClick()
                }
            },
            modifier = Modifier.weight(1f)
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
            value = "120.03",
            onValueChange = {},
            note = "",
            onNoteChange = {},
            category = null,
            categories = emptyList(),
            onCategoryClick = {},
            context = LocalContext.current,
            onSaveClick = {}
        )
    }
}