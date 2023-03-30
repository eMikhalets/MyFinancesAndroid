package com.emikhalets.core

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatTest {

    @Test
    fun round_value() {
        var input = 10_000.10
        var answer = "10000.10"
        var result = input.round()
        assertEquals(answer, result)
    }

    @Test
    fun format_value() {
        var input = 1_000_000.10
        var answer = "1 000 000.10"
        var result = input.formatValue()
        assertEquals(answer, result)
    }

    @Test
    fun format_value_to_double() {
        var input = 1_000_000.10
        var result = input.formatValue().toValue()
        assertEquals(input, result, 0.0)
    }
}