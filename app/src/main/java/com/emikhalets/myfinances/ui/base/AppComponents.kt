package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    emptyError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = emptyError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            capitalization = keyboardCapitalization
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        modifier = modifier
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppPager(
    navController: NavHostController,
    scope: CoroutineScope,
    pagerState: PagerState,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed { index, name ->
            Tab(
                selected = pagerState.currentPage == index,
                text = { Text(name) },
                onClick = { scope.launch { pagerState.scrollToPage(index) } }
            )
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        content = { page -> content(page) }
    )
}

@Composable
fun <T> AppVerticalList(
    list: List<T>,
    content: @Composable (T) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { item ->
            content(item)
        }
    }
}