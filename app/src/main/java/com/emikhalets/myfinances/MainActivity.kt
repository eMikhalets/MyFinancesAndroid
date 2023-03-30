package com.emikhalets.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.presentation.navigation.NavGraph
import com.emikhalets.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                App(navController)
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavGraph(navController)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    App(rememberNavController())
}