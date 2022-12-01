package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.appBackground
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.presentation.theme.textPrimary
import com.emikhalets.myfinances.presentation.theme.textSecondary
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppPager(
    scope: CoroutineScope,
    pagerState: PagerState,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.appBackground,
        contentColor = MaterialTheme.colors.textPrimary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = MaterialTheme.colors.boxBackground
            )
        }
    ) {
        tabs.forEachIndexed { index, name ->
            Tab(
                selected = pagerState.currentPage == index,
                text = { TextPrimary(name) },
                onClick = { scope.launch { pagerState.scrollToPage(index) } },
                modifier = Modifier.background(
                    if (pagerState.currentPage == index) MaterialTheme.colors.boxBackground
                    else MaterialTheme.colors.appBackground
                )
            )
        }
    }
    Divider(color = MaterialTheme.colors.textSecondary)
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        content = { page -> content(page) }
    )
}

@Composable
fun CategoriesDropMenu(
    item: Category,
    list: List<Category>,
    onSelect: (Category) -> Unit,
) {
    var selected by remember { mutableStateOf(item) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(item) { selected = item }

    Column {
        AppTextField(
            value = selected.name,
            onValueChange = {},
            label = stringResource(R.string.label_category),
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { entry ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selected = entry
                        expanded = false
                        onSelect(selected)
                    }
                ) {
                    TextPrimary(text = entry.name)
                }
            }
        }
    }
}

@Composable
fun TransactionTypeChooser(
    type: TransactionType,
    onTypeSelect: (TransactionType) -> Unit,
) {
    Row(Modifier.fillMaxWidth()) {
        AppText(
            text = stringResource(R.string.expenses),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Expense) }
                .borderTypeExpense(type)
                .padding(8.dp)
        )
        AppText(
            text = stringResource(R.string.incomes),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Income) }
                .borderTypeIncome(type)
                .padding(8.dp)
        )
    }
}

@Composable
private fun Modifier.borderTypeExpense(type: TransactionType) = when (type) {
    TransactionType.Expense -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Composable
private fun Modifier.borderTypeIncome(type: TransactionType) = when (type) {
    TransactionType.Income -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Composable
fun AppKeyboard(onClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        val (
            oneRef, twoRef, threeRef,
            fourRef, fiveRef, sixRef,
            sevenRef, eightRef, nineRef,
            zeroRef, delRef,
        ) = createRefs()

        val chainOne = createHorizontalChain(oneRef, twoRef, threeRef,
            chainStyle = ChainStyle.Packed)

        val chainTwo = createHorizontalChain(fourRef, fiveRef, sixRef,
            chainStyle = ChainStyle.Packed)

        val chainThree = createHorizontalChain(sevenRef, eightRef, nineRef,
            chainStyle = ChainStyle.Packed)

        val chainBottom = createHorizontalChain(zeroRef, delRef,
            chainStyle = ChainStyle.Packed)

        AppButton(
            text = "1",
            onClick = { onClick("1") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(oneRef) { top.linkTo(parent.top) }
        )

        AppButton(
            text = "2",
            onClick = { onClick("2") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(twoRef) { top.linkTo(parent.top) }
        )

        AppButton(
            text = "3",
            onClick = { onClick("3") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(threeRef) { top.linkTo(parent.top) }
        )

        AppButton(
            text = "4",
            onClick = { onClick("4") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(fourRef) { top.linkTo(oneRef.bottom) }
        )

        AppButton(
            text = "5",
            onClick = { onClick("5") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(fiveRef) { top.linkTo(oneRef.bottom) }
        )

        AppButton(
            text = "6",
            onClick = { onClick("6") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(sixRef) { top.linkTo(oneRef.bottom) }
        )

        AppButton(
            text = "7",
            onClick = { onClick("7") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(sevenRef) { top.linkTo(fourRef.bottom) }
        )

        AppButton(
            text = "8",
            onClick = { onClick("8") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(eightRef) { top.linkTo(fourRef.bottom) }
        )

        AppButton(
            text = "9",
            onClick = { onClick("9") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(nineRef) { top.linkTo(fourRef.bottom) }
        )

        AppButton(
            text = "0",
            onClick = { onClick("0") },
            modifier = Modifier
                .padding(4.dp)
                .size((56 * 2 + 8).dp, 56.dp)
                .constrainAs(zeroRef) {
                    top.linkTo(sevenRef.bottom)
                }
        )

        AppButton(
            text = "X",
            onClick = { onClick("X") },
            modifier = Modifier
                .padding(4.dp)
                .size(56.dp)
                .constrainAs(delRef) { top.linkTo(sevenRef.bottom) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesDropMenuPreview() {
    AppTheme {
        Box(Modifier.padding(8.dp)) {
            CategoriesDropMenu(
                item = Category("Some category", TransactionType.Expense),
                list = emptyList(),
                onSelect = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionTypeChooserPreview() {
    AppTheme {
        Box(Modifier.padding(8.dp)) {
            TransactionTypeChooser(
                type = TransactionType.Expense,
                onTypeSelect = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppKeyboardPreview() {
    AppTheme {
        AppKeyboard {}
    }
}