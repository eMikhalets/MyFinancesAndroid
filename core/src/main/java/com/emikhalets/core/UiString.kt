package com.emikhalets.core

import android.content.res.Resources
import androidx.annotation.StringRes
import com.emikhalets.myfinances.R

sealed class UiString {

    data class Message(
        val value: String?,
    ) : UiString()

    class Resource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UiString()

    fun asString(): String = when (this) {
        is Message -> value ?: Resources.getSystem().getString(R.string.error_internal)
        is Resource -> Resources.getSystem().getString(resId, *args)
    }

    companion object {

        fun create(value: String?): UiString = Message(value)

        fun create(@StringRes resId: Int, vararg args: Any): UiString = Resource(resId, args)
    }
}