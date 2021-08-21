package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.base.AppText
import com.emikhalets.myfinances.ui.base.TextValue
import com.emikhalets.myfinances.utils.getCurrentWalletId
import com.emikhalets.myfinances.utils.toast

@Composable
fun SummaryScreen(
    navController: NavHostController,
    viewModel: SummaryVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect("init") {
        viewModel.getSummaryMonth(context.getCurrentWalletId())
    }
    LaunchedEffect(state) {
        if (state.error != null) toast(context, state.errorMessage())
    }

    Column(Modifier.fillMaxSize()) {
        state.monthTransactions.forEach { (key, value) ->
            Row {
                AppText(text = key.name)
                TextValue(value)
            }
        }
    }
}