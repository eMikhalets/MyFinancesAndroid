package com.emikhalets.myfinances.ui.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.months
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconsList(
    iconError: Boolean,
    onSelectIcon: (Int) -> Unit
) {
    var selected by remember { mutableStateOf(-1) }
    val icons = listOf(
        com.emikhalets.myfinances.utils.enums.MyIcons.DotsHollow,
        com.emikhalets.myfinances.utils.enums.MyIcons.Cutlery,
        com.emikhalets.myfinances.utils.enums.MyIcons.Gift,
        com.emikhalets.myfinances.utils.enums.MyIcons.Gym,
        com.emikhalets.myfinances.utils.enums.MyIcons.Healthcare,
        com.emikhalets.myfinances.utils.enums.MyIcons.Loan,
        com.emikhalets.myfinances.utils.enums.MyIcons.Mortarboard,
        com.emikhalets.myfinances.utils.enums.MyIcons.Tent,
        com.emikhalets.myfinances.utils.enums.MyIcons.Train,
        com.emikhalets.myfinances.utils.enums.MyIcons.Tshirt,
        com.emikhalets.myfinances.utils.enums.MyIcons.User,
        com.emikhalets.myfinances.utils.enums.MyIcons.Wifi,
        com.emikhalets.myfinances.utils.enums.MyIcons.Salary,
        com.emikhalets.myfinances.utils.enums.MyIcons.SaveMoney
    )

    LazyVerticalGrid(
        cells = GridCells.Adaptive(60.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 4.dp,
                shape = RoundedCornerShape(8.dp),
                color = if (iconError) {
                    MaterialTheme.colors.error
                } else {
                    Color.Transparent
                }
            )
    ) {
        items(icons) { icon ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        selected = icon.id
                        onSelectIcon(icon.id)
                    }
                    .background(
                        color = if (selected == icon.id) {
                            MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        } else {
                            MaterialTheme.colors.surface
                        },
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                AppIcon(
                    drawable = icon.icon,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DateChooser(
    month: Int,
    year: Int,
    onBackDateClick: () -> Unit,
    onForwardDateClick: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppIcon(
                drawable = R.drawable.ic_arrow_back,
                modifier = Modifier
                    .clickable { onBackDateClick() }
                    .padding(16.dp)
            )
            AppText(
                text = "${months()[month]}, $year",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            AppIcon(
                drawable = R.drawable.ic_arrow_forward,
                modifier = Modifier
                    .clickable { onForwardDateClick() }
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
            month = 8,
            year = 2021,
            onBackDateClick = {},
            onForwardDateClick = {}
        )
    }
}