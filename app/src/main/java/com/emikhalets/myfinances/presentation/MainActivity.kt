package com.emikhalets.myfinances.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.Prefs
import com.emikhalets.myfinances.utils.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by viewModels()

    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (prefs.currentWalletId == Prefs.NO_WALLET_ID) {
            viewModel.createDefaultWallet(getString(R.string.default_wallet_name))
        }

        setContent {
            val navController = rememberNavController()

            MyFinancesTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}