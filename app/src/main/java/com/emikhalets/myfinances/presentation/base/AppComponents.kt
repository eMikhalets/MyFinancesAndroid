package com.emikhalets.myfinances.presentation.base

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.formatForDateChooser
import com.emikhalets.myfinances.utils.minusMonth
import com.emikhalets.myfinances.utils.plusMonth
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppPager(
    scope: CoroutineScope,
    pagerState: PagerState,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
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
    Divider()
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        content = { page -> content(page) }
    )
}

@Composable
fun <T> AppVerticalList(
    list: List<T>,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(list) { item ->
            content(item)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DateChooser(date: Long, onDateChange: (Long) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppIcon(
                drawable = R.drawable.ic_arrow_back,
                modifier = Modifier
                    .clickable { onDateChange(date.minusMonth().timeInMillis) }
                    .padding(16.dp)
            )
            AnimatedContent(
                targetState = date,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally({ width -> width }) + fadeIn() with
                                slideOutHorizontally({ width -> -width }) + fadeOut()
                    } else {
                        slideInHorizontally({ width -> -width }) + fadeIn() with
                                slideOutHorizontally({ width -> width }) + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) { targetDate ->
                AppText(
                    text = targetDate.formatForDateChooser(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            AppIcon(
                drawable = R.drawable.ic_arrow_forward,
                modifier = Modifier
                    .clickable { onDateChange(date.plusMonth().timeInMillis) }
                    .padding(16.dp)
            )
        }
        Divider(color = MaterialTheme.colors.secondary)
    }
}

// ================================== Previews ==================================

@Preview(showBackground = true)
@Composable
private fun DateChooserPreview() {
    MyFinancesTheme {
        DateChooser(
            date = Calendar.getInstance().timeInMillis,
            onDateChange = {}
        )
    }
}