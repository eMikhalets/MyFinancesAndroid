package com.emikhalets.myfinances.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.emikhalets.myfinances.R
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val DEFAULT_ERROR = "null"

suspend inline fun <T : Any> runDatabaseRequest(crossinline block: suspend () -> T): Result<T> {
    return withContext(Dispatchers.Default) {
        try {
            Result.success(block())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}

fun Double.toValue(): String {
    return try {
        "$this ₽"
    } catch (ex: NumberFormatException) {
        "- ₽"
    }
}

fun Long?.toDate(): String {
    if (this == null) return "no date"
    val date = Date(this)
    val formatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun Long?.toLabelDate(): String {
    if (this == null) return "no date"
    val date = Date(this)
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

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

fun String.safeToDouble(): Double {
    return try {
        this.toDouble()
    } catch (ex: Exception) {
        ex.printStackTrace()
        0.0
    }
}