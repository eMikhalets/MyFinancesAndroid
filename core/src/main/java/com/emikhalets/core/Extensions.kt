package com.emikhalets.core

import android.content.res.Resources
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.*

fun getString(@StringRes res: Int): String = Resources.getSystem().getString(res)

inline fun String.toDoubleSafe(crossinline onError: () -> Unit = {}): Double {
    if (this.isEmpty() || this.isBlank()) return 0.0
    return try {
        val converted: Double = this.toDouble()
        converted / 100
    } catch (ex: Exception) {
        ex.printStackTrace()
        onError()
        0.0
    }
}

fun Long?.toDate(): String {
    this ?: return "no date"
    val formatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long?.formatDate(pattern: String = "dd MMM yyyy"): String? {
    this ?: return null
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(this))
}