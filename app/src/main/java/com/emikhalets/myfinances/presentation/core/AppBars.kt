package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Category
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.presentation.AppScreen
import com.emikhalets.myfinances.presentation.navigateToCategories
import com.emikhalets.myfinances.presentation.navigateToWallets
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.presentation.theme.textPrimary

@Composable
fun ScreenScaffold(
    toolbar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = { toolbar() },
        content = { Box(Modifier.padding(it)) { content() } }
    )
}

@Composable
fun AppToolbar(
    navController: NavHostController,
    title: String,
    icon: ImageVector? = Icons.Rounded.ArrowBack,
    actions: List<ImageVector> = listOf(),
) {
    TopAppBar(
        title = {
            TextPrimary(
                text = title,
                size = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = icon?.let {
            {
                AppIcon(
                    imageVector = icon,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
        } ?: run { null },
        actions = {
            actions.forEach { iconVector ->
                AppIcon(
                    imageVector = iconVector,
                    modifier = Modifier
                        .clickable { navController.navigate(iconVector) }
                        .padding(start = 16.dp)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.boxBackground,
        contentColor = MaterialTheme.colors.textPrimary,
        elevation = 0.dp
    )
}

@Composable
fun MainToolbar(navController: NavHostController) {
    AppToolbar(
        navController = navController,
        title = stringResource(AppScreen.Main.title),
        icon = null,
        actions = listOf(Icons.Rounded.Category, Icons.Rounded.AccountBalanceWallet)
    )
}

private fun NavHostController.navigate(icon: ImageVector) {
    when (icon) {
        Icons.Rounded.Category -> navigateToCategories()
        Icons.Rounded.AccountBalanceWallet -> navigateToWallets()
    }
}

@Preview(showBackground = true)
@Composable
private fun MainToolBarPreview() {
    AppTheme {
        MainToolbar(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun AppToolBarPreview() {
    AppTheme {
        AppToolbar(
            navController = rememberNavController(),
            title = "Some title"
        )
    }
}