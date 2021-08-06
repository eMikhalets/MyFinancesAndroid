package com.emikhalets.myfinances.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.ui.screens.AppScreen
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MyFinancesTheme {
                AppScreen(
                    navController = navController
                )
            }
        }
    }
}