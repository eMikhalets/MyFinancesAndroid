package com.emikhalets.myfinances.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.*

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