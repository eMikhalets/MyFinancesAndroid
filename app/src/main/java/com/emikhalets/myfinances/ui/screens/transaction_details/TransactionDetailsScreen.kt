package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun TransactionDetailsScreen(
    navController: NavHostController,
    transactionId: Long,
    viewModel: TransactionDetailsVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<Category?>(null) }
    var type by remember { mutableStateOf(TransactionType.None) }

    LaunchedEffect("init") {
        viewModel.getTransaction(transactionId)
    }
    LaunchedEffect(type) {
        if (type != TransactionType.None) {
            viewModel.getCategories(type.value)
        }
    }
    LaunchedEffect(state.transaction) {
        if (state.transaction != null) {
            note = state.transaction.transaction.note
            value = state.transaction.transaction.amount.toString()
            category = state.transaction.category
            type = TransactionType.get(state.transaction.transaction.type)
        }
    }
    LaunchedEffect(state) {
        if (state.error != null) toast(context, state.error)
        if (state.deletedTransaction) navController.popBackStack()
    }

    TransactionDetailsScreen(
        navController = navController,
        transactionType = type,
        onTypeChange = {
            type = it
            viewModel.getCategories(it.value)
        },
        value = value,
        onValueChange = {
            value = if (it == Keyboard.DEL.value) {
                if (value.isEmpty()) ""
                else value.substring(0, value.length - 1)
            } else {
                (value + it).formatValue()
            }
        },
        note = note,
        onNoteChange = { note = it },
        category = category,
        categories = state.categories,
        onCategoryClick = { category = it },
        onSaveClick = {
            viewModel.saveTransaction(
                context.getCurrentWalletId(),
                category!!,
                value.toDouble(),
                type.value,
                note
            )
        },
        onDeleteClick = { viewModel.deleteTransaction() }
    )
}

@Composable
fun TransactionDetailsScreen(
    navController: NavHostController,
    transactionType: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    category: Category?,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    ScreenScaffold(
        navController = navController,
        title = stringResource(R.string.title_transaction)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TransactionTypeChooser(type = transactionType, onSelectType = onTypeChange)
            Spacer(Modifier.height(16.dp))
            ValueField(value)
            Spacer(Modifier.height(16.dp))
            NoteTextField(note = note, onNoteChange = onNoteChange)
            Spacer(Modifier.height(16.dp))
            Keyboard(onClick = onValueChange)
            Spacer(Modifier.height(16.dp))
            CategoriesLayout(
                selected = category,
                categories = categories,
                onCategoryClick = onCategoryClick
            )
            Spacer(Modifier.height(16.dp))
            ControlButtons(onSaveClick = onSaveClick, onDeleteClick = onDeleteClick)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun TransactionTypeChooser(type: TransactionType, onSelectType: (TransactionType) -> Unit) {

    @Composable
    fun getColor(comparative: TransactionType): Color {
        return if (type == comparative) MaterialTheme.colors.primary.copy(alpha = 0.5f)
        else Color.Transparent
    }

    Row(Modifier.fillMaxWidth()) {
        AppText(
            text = stringResource(R.string.expense),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectType(TransactionType.Expense) }
                .background(getColor(TransactionType.Expense))
                .padding(8.dp)
        )
        AppText(
            text = stringResource(R.string.income),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectType(TransactionType.Income) }
                .background(getColor(TransactionType.Income))
                .padding(8.dp)
        )
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
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_1.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_2.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_3.value,
                onClick = onClick
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_4.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_5.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_6.value,
                onClick = onClick
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_7.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_8.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_9.value,
                onClick = onClick
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.DOT.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.NUM_0.value,
                onClick = onClick
            )
            KeyboardButton(
                value = com.emikhalets.myfinances.utils.enums.Keyboard.DEL.value,
                onClick = onClick
            )
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
fun CategoriesLayout(
    selected: Category?,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
    ) {
        AppText(
            text = selected?.name ?: stringResource(R.string.choose_category),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .clip(RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
        AnimateExpandCollapse(visible = expanded, duration = 300) {
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            categories.forEach { category ->
                AppText(
                    text = category.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                        .padding(16.dp)
                )
            }
            AppText(
                text = stringResource(R.string.new_category),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ControlButtons(onSaveClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.cancel),
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f)
        )
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsPreview() {
    MyFinancesTheme {
        TransactionDetailsScreen(
            navController = rememberNavController(),
            transactionType = TransactionType.Expense,
            onTypeChange = {},
            value = "120.03",
            onValueChange = {},
            note = "",
            onNoteChange = {},
            category = null,
            categories = emptyList(),
            onCategoryClick = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}