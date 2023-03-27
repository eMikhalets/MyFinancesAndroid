package com.emikhalets.presentation.screens.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.R
import com.emikhalets.presentation.core.AppButton
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.textSecondary
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    onCategoryClick: (categoryId: Long, type: TransactionType) -> Unit,
    onAddCategoryClick: (type: TransactionType) -> Unit,
    onBackClick: () -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(title = stringResource(Screen.Categories.title), onBackClick = onBackClick)
        ScreenContent(
            expenseList = uiState.expenseList,
            incomeList = uiState.incomeList,
            onCategoryClick = { id, type -> onCategoryClick(id, type) },
            onAddCategoryClick = { type -> onAddCategoryClick(type) }
        )
    }

    val errorMessage = error
    if (errorMessage != null) {
        MessageDialog(
            message = errorMessage.asString(),
            onDismiss = { error = null }
        )
    }
}

@Composable
private fun ScreenContent(
    expenseList: List<CategoryEntity>,
    incomeList: List<CategoryEntity>,
    onCategoryClick: (Long, TransactionType) -> Unit,
    onAddCategoryClick: (type: TransactionType) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Pager(
            expenseList = expenseList,
            incomeList = incomeList,
            onCategoryClick = onCategoryClick,
            onAddCategoryClick = onAddCategoryClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.Pager(
    expenseList: List<CategoryEntity>,
    incomeList: List<CategoryEntity>,
    onCategoryClick: (Long, TransactionType) -> Unit,
    onAddCategoryClick: (TransactionType) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0)

    Tabs(
        tabIndex = pagerState.currentPage,
        titles = listOf(
            stringResource(R.string.error_internal),
            stringResource(R.string.error_internal),
        ),
        onTabClick = { index -> scope.launch { pagerState.scrollToPage(index) } }
    )
    HorizontalPager(
        state = pagerState,
        pageCount = 2,
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
    ) { page ->
        CategoriesList(
            categories = when (page) {
                0 -> expenseList
                1 -> incomeList
                else -> throw Exception("Unknown page index $page")
            },
            onCategoryClick = { id ->
                val type = getCategoryType(page)
                onCategoryClick(id, type)
            },
            onAddCategoryClick = {
                val type = getCategoryType(page)
                onAddCategoryClick(type)
            },
        )
    }
}

private fun getCategoryType(page: Int): TransactionType {
    return when (page) {
        0 -> TransactionType.Expense
        1 -> TransactionType.Income
        else -> throw Exception("Unknown page index $page")
    }
}

@Composable
private fun Tabs(
    tabIndex: Int,
    titles: List<String>,
    onTabClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = tabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.tabIndicatorOffset(tabPositions[tabIndex]))
        },
        modifier = modifier.fillMaxWidth(),
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = tabIndex == index,
                onClick = { onTabClick(index) },
                text = { Text(text = title) },
            )
        }
    }
}

@Composable
private fun CategoriesList(
    categories: List<CategoryEntity>,
    onCategoryClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onCategoryClick = onCategoryClick
                )
            }
        }
        Divider()
        AppButton(
            text = stringResource(R.string.add),
            onClick = onAddCategoryClick
        )
    }
}

@Composable
private fun CategoryItem(category: CategoryEntity, onCategoryClick: (Long) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = category.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCategoryClick(category.id) }
                .padding(8.dp)
        )
        Divider(color = MaterialTheme.colors.textSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            expenseList = listOf(
                CategoryEntity(0, "Name 1", TransactionType.Expense),
                CategoryEntity(0, "Name 1", TransactionType.Expense),
                CategoryEntity(0, "Name 1", TransactionType.Expense),
                CategoryEntity(0, "Name 1", TransactionType.Expense),
                CategoryEntity(0, "Name 1", TransactionType.Expense),
                CategoryEntity(0, "Name 1", TransactionType.Expense),
            ),
            incomeList = listOf(
                CategoryEntity(0, "Name 1", TransactionType.Income),
                CategoryEntity(0, "Name 1", TransactionType.Income),
                CategoryEntity(0, "Name 1", TransactionType.Income),
                CategoryEntity(0, "Name 1", TransactionType.Income),
                CategoryEntity(0, "Name 1", TransactionType.Income),
                CategoryEntity(0, "Name 1", TransactionType.Income),
            ),
            onCategoryClick = {},
            onAddCategoryClick = {}
        )
    }
}