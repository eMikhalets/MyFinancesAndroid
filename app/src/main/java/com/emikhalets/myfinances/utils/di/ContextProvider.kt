package com.emikhalets.myfinances.utils.di

import android.content.Context
import javax.inject.Inject

class ContextProvider @Inject constructor(private val context: Context) {

    fun getContext(): Context = context
}