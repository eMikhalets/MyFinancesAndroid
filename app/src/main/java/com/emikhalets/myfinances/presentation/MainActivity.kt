package com.emikhalets.myfinances.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.base.AppBottomBar
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.SharedPrefs
import com.emikhalets.myfinances.utils.navigation.AppNavGraph
import com.emikhalets.myfinances.utils.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPrefs.getCurrentWalletId(this) <= 0) {
            viewModel.createDefaultWallet(this, getString(R.string.default_wallet_name))
        }

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            MyFinancesTheme {
                Scaffold(
                    bottomBar = {
                        AppBottomBar(
                            navController = navController,
                            currentDestination = currentDestination,
                            items = Screen.getBottomNavigationScreens()
                        )
                    },
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