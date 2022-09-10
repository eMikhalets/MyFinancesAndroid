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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.navigation.AppScreen
import com.emikhalets.myfinances.utils.navigation.navigateToCategories
import com.emikhalets.myfinances.utils.navigation.navigateToWallets

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
    icon: Int? = null,
    actions: List<ImageVector> = listOf(),
) {
    TopAppBar(
        title = {
            AppText(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            when (icon) {
                null -> AppIcon(
                    imageVector = Icons.Rounded.ArrowBack,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                else -> AppIcon(icon)
            }
        },
        actions = {
            actions.forEach { iconVector ->
                AppIcon(
                    imageVector = iconVector,
                    modifier = Modifier.clickable { navController.navigate(iconVector) }
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp
    )
}

@Composable
fun MainToolbar(navController: NavHostController) {
    AppToolbar(
        navController = navController,
        title = stringResource(AppScreen.Main.title),
        icon = R.mipmap.ic_launcher,
        actions = listOf(Icons.Rounded.Category, Icons.Rounded.AccountBalanceWallet)
    )
}

private fun NavHostController.navigate(icon: ImageVector) {
    when (icon) {
        Icons.Rounded.Category -> navigateToCategories()
        Icons.Rounded.AccountBalanceWallet -> navigateToWallets()
    }
}