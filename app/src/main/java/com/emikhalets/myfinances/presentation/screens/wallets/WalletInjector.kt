package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.lifecycle.ViewModel
import com.emikhalets.myfinances.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletInjector @Inject constructor(val prefs: Prefs) : ViewModel()