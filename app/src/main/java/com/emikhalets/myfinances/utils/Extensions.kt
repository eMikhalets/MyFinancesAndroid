package com.emikhalets.myfinances.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
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

/**
 * Форматирует строку ти денежному типу. Не дает ввести более 2 элементов после точки.
 * Форматирует запятую в точку
 */
fun String.formatValue(): String {
    return when {
        this.first() == '.' -> {
            "0$this"
        }
        this.count { it == '.' } > 1 -> {
            this.substring(0, this.length - 1)
        }
        this.contains('.') && this.split('.')[1].length > 2 -> {
            this.substring(0, this.length - 1)
        }
        else -> {
            this
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
    val formatter = SimpleDateFormat("dd MMMM y", Locale.getDefault())
    return formatter.format(date)
}

fun toast(context: Context, @StringRes res: Int) {
    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
}

fun toast(context: Context, exception: Exception) {
    Toast.makeText(context, exception.errorMessage(), Toast.LENGTH_SHORT).show()
}

fun Exception.errorMessage(): String {
    return this.message ?: "No message"
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.sectionBorder(): Modifier = composed {
    this.border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
}