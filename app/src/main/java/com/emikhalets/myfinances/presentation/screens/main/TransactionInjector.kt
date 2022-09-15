package com.emikhalets.myfinances.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.emikhalets.myfinances.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionInjector @Inject constructor(val prefs: Prefs) : ViewModel()