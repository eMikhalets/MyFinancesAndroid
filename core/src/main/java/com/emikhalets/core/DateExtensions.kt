package com.emikhalets.core

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Long.getStartOfMonth(): Long {
    val calendar = Calendar.getInstance()
    calendar.apply {
        timeInMillis = this@getStartOfMonth
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
    }
    return calendar.timeInMillis
}

fun Long.getEndOfMonth(): Long {
    val initial = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .toLocalDate()
    val date = initial
        .withDayOfMonth(initial.month.length(initial.isLeapYear))
        .atTime(23, 59, 59)
    return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.getStartOfNextMonth(): Long {
    val initial = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .toLocalDate()
    val date = initial
        .withDayOfMonth(1)
        .atStartOfDay()
        .plusMonths(1)
    return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.getStartOfPrevMonth(): Long {
    val initial = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .toLocalDate()
    val date = initial
        .withDayOfMonth(1)
        .atStartOfDay()
        .minusMonths(1)
    return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}