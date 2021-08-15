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

    var isDot = false
    var dotIndex = 0
    var out = ""
    if (text.text.isNotEmpty()) {
        for (i in text.text.indices) {
            if (text.text[i] == '.' || text.text[i] == ',') {
                out += "."
                dotIndex = i
                isDot = true
            } else {
                out +=text.text[i]
//                out += if (isDot && text.text.substring(dotIndex, text.text.length - 1).length >= 2) {
//                    ""
//                } else {
//                    text.text[i]
//                }
            }
        }
        out += " ₽"
    }

    val currencyOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return if (isDot) offset + 1
            else offset
        }

        override fun transformedToOriginal(offset: Int): Int {
            return if (isDot) offset - 1
            else offset
        }
    }

    return TransformedText(AnnotatedString(out), currencyOffsetTranslator)
}