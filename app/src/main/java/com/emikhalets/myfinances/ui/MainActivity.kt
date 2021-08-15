package com.emikhalets.myfinances.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.ui.base.AppBottomBar
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MyFinancesTheme {
                Scaffold(
                    bottomBar = { AppBottomBar(navController = navController) },
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}