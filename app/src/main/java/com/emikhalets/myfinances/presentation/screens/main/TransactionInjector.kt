package com.emikhalets.myfinances.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.utils.di.ContextProvider
import com.emikhalets.myfinances.utils.Prefs
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionInjector @Inject constructor(
    private val contextProvider: ContextProvider,
    val prefs: Prefs,
) : ViewModel() {

    fun getDefCategory(type: TransactionType): Category {
        return Category.getDefault(contextProvider.getContext(), type)
    }
}