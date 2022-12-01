package com.emikhalets.myfinances.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// ========================================
// Global constants

const val DEFAULT_ERROR = "null"

// ========================================
// Global constants

suspend inline fun <T : Any> execute(crossinline block: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        kotlin.runCatching { block() }.onFailure { it.printStackTrace() }
    }
}

// ========================================
// Primitive conversion

inline fun String.safeToDouble(crossinline onError: () -> Unit = {}): Double {
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

fun Double.toMoney(): String {
    return (this * 100).toInt().toString()
}

fun String.toMoney(): String {
    return try {
        val converted: Double = this.toDouble()
        (converted * 100).toInt().toString()
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

// ========================================
// Date formatting

fun Long?.toDate(): String {
    this ?: return "no date"
    val formatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long?.toLabelDate(): String {
    if (this == null) return "no date"
    val date = Date(this)
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

// ========================================
// Toasts and snackBars

fun toast(context: Context, @StringRes res: Int) {
    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
}

fun toast(context: Context, message: String) {
    if (message.isEmpty()) return
    Toast.makeText(context, message.errorOrDefault(context), Toast.LENGTH_SHORT).show()
}

private fun String.errorOrDefault(context: Context): String {
    return if (this == DEFAULT_ERROR) context.getString(R.string.default_error) else this
}

// ========================================
// Compose

@Composable
fun String.formatValue(): String {
    val thousandsString = this
    val resourceString = stringResource(R.string.app_money_value, thousandsString)
    return this
}

fun String.appKeyboardInput(oldValue: String): String {
    return if (this == "X") {
        ""
    } else {
        oldValue + this
    }
}