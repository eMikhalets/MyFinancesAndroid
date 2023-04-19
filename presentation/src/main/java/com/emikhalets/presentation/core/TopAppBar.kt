package com.emikhalets.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun AppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(title) },
        elevation = 0.dp,
        navigationIcon = if (onBackClick != null) {
            { NavigationIcon(onIconClick = onBackClick) }
        } else null,
        modifier = modifier
    )
}

@Composable
private fun NavigationIcon(onIconClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onIconClick() }
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back navigation icon")
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppTopAppBar(title = "Test", onBackClick = {})
    }
}