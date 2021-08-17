package com.emikhalets.myfinances.utils

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