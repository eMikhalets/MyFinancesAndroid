package com.emikhalets.myfinances.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return currencyFilter(text)
    }
}

fun currencyFilter(text: AnnotatedString): TransformedText {

    var out = ""
    var isDot = false
    for (i in text.text.indices) {
        when {
            text.text[i] == '.' || text.text[i] == ',' -> {
                if (!isDot) {
                    out += "."
                    isDot = true
                }
            }
            else -> {
                out += text.text[i]
            }
        }
    }
    if (text.text.isNotEmpty()) out += " ₽"

    val currencyOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset
        }
    }

    return TransformedText(AnnotatedString(out), currencyOffsetTranslator)
}