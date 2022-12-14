package com.emikhalets.myfinances.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.screens.wallets.WalletDialog
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.utils.Prefs
import com.emikhalets.myfinances.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var prefs: Prefs
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = viewModel.state
            val context = LocalContext.current
            val navController = rememberNavController()
            var visibleAppScreens by remember {
                mutableStateOf(prefs.currentWalletId != Prefs.NO_WALLET_ID)
            }

            LaunchedEffect(state.createdWalletId) {
                if (state.createdWalletId != 0L) prefs.currentWalletId = state.createdWalletId
                visibleAppScreens = prefs.currentWalletId != Prefs.NO_WALLET_ID
            }

            LaunchedEffect(state.error) { toast(context, state.error) }

            AppTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    if (visibleAppScreens) {
                        AppNavGraph(navController = navController)
                    } else {
                        WalletDialog(
                            wallet = null,
                            cancelable = false,
                            onSaveClick = { wallet ->
                                viewModel.saveWallet(
                                    wallet,
                                    context.getString(R.string.default_category_name)
                                )
                            },
                            onDismiss = {},
                            onDeleteClick = {}
                        )
                    }
                }
            }
        }
    }
}