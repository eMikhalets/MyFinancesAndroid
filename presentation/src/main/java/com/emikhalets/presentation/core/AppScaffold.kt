package com.emikhalets.presentation.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    Scaffold(bottomBar = { AppBottomBar(navController) },
        backgroundColor = MaterialTheme.colors.surface) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            content()
        }
    }
}