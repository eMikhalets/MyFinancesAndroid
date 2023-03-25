package com.emikhalets.myfinances.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.emikhalets.presentation.navigation.NavGraph
import com.emikhalets.presentation.theme.appBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(color = MaterialTheme.colors.appBackground) {
                    NavGraph(navController)
                }
            }
        }
    }
}