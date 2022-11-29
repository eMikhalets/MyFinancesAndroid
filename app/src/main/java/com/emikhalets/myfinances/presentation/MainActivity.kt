package com.emikhalets.myfinances.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.presentation.navigation.AppNavGraph
import com.emikhalets.myfinances.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    AppNavGraph(navController)
                }
            }
        }
    }
}