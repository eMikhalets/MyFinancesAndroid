package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.base.AppScaffold
import com.emikhalets.myfinances.ui.screens.transactions.AddTransaction

@Composable
fun SummaryScreen(
    navController: NavHostController
) {
    AppScaffold(
        navController = navController,
        showTopBar = true,
        showBottomBar = true
    ) {
        Column(Modifier.fillMaxSize()) {
            Text("SummaryScreen")
        }
    }
}