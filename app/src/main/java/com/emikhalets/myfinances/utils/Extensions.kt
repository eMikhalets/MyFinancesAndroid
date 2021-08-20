package com.emikhalets.myfinances.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Форматирует строку ти денежному типу. Не дает ввести более 2 элементов после точки.
 * Форматирует запятую в точку
 */
fun String.formatValue(): String {
    var result = ""
    var dotIndex = -1
    var counter = 0
    this.forEachIndexed { index, item ->
        when {
            item == '.' || item == ',' -> {
                if (dotIndex < 0) result += '.'
                dotIndex = index
            }
            dotIndex > 0 -> {
                if (counter < 2) {
                    result += item
                    counter++
                } else {
                    return@forEachIndexed
                }
            }
            else -> result += item
        }
    }
    return result
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

fun toast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}