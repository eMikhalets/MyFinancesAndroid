package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SummaryScreen(
    navController: NavHostController
) {
    Column(Modifier.fillMaxSize()) {
        Text("SummaryScreen")
    }
}