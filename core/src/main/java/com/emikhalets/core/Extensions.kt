package com.emikhalets.core

import android.content.res.Resources
import androidx.annotation.StringRes
import java.text.NumberFormat
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

fun Double.round(): String {
    return String.format(Locale.US, "%.2f", this)
}

//fun Double.formatValue(): String {
//    val nf = NumberFormat.getCurrencyInstance()
//    val dfs = (nf as DecimalFormat).decimalFormatSymbols
//    dfs.currencySymbol = ""
//    nf.decimalFormatSymbols = dfs
//    return nf.format(this)
//        .trim()
//        .replace(" ", " ")
//        .replace(",", " ")
//}

fun Double.formatAmount(): String {
    return NumberFormat.getCurrencyInstance().format(this).trim()
}

fun String.toValue(): Double {
    return try {
        this.replace(" ", "").toDouble()
    } catch (e: Exception) {
        0.0
    }
}