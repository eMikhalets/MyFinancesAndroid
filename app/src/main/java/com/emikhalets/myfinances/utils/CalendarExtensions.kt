package com.emikhalets.myfinances.utils

import java.util.*

fun Calendar.getMaxDayOfMonth(timestamp: Long = Date().time): Int {
    this.timeInMillis = timestamp
    return getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun Calendar.getMinDayOfMonth(timestamp: Long = Date().time): Int {
    this.timeInMillis = timestamp
    return getActualMinimum(Calendar.DAY_OF_MONTH)
}

fun Calendar.getMaxTSOfMonth(timestamp: Long = Date().time): Long {
    this.timeInMillis = timestamp
    this.set(Calendar.DAY_OF_MONTH, this.getMaxDayOfMonth(timestamp))
    return this.timeInMillis
}

fun Calendar.getMinTSOfMonth(timestamp: Long = Date().time): Long {
    this.timeInMillis = timestamp
    this.set(Calendar.DAY_OF_MONTH, this.getMinDayOfMonth(timestamp))
    return this.timeInMillis
}

fun Long.plusMonth(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.add(Calendar.MONTH, 1)
    return calendar
}

fun Long.minusMonth(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.add(Calendar.MONTH, -1)
    return calendar
}

fun Calendar.year(): Int {
    return this.get(Calendar.YEAR)
}

fun Calendar.month(): Int {
    return this.get(Calendar.MONTH)
}

fun Calendar.monthName(): String {
    return try {
        months()[this.month()]
    } catch (ex: NumberFormatException) {
        ex.printStackTrace()
        "-"
    }
}

fun Long.formatForDateChooser(): String {
    return try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        "${months()[calendar.month()]}, ${calendar.year()}"
    } catch (ex: NumberFormatException) {
        ex.printStackTrace()
        "-, -"
    }
}

fun months(): List<String> {
    return listOf(
        "Январь",
        "Февраль",
        "Март",
        "Апрель",
        "Май",
        "Июнь",
        "Июль",
        "Август",
        "Сентябрь",
        "Октябрь",
        "Ноябрь",
        "Декабрь"
    )
}