package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.boxBackground
import com.emikhalets.myfinances.presentation.theme.textPrimary
import com.emikhalets.myfinances.utils.formatMoney

@Composable
fun TransactionKeyboard(value: Double, onValueChange: (Double) -> Unit) {
    var isDotPrinted by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Text(text = value.formatMoney(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            KeyboardButton(value = 1,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 2,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 3,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            KeyboardButton(value = 4,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 5,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 6,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            KeyboardButton(value = 7,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 8,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = 9,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            KeyboardButton(value = DOT, onClick = {
                if (!isDotPrinted) {
                    onValueChange(value.changeValue(it, isDotPrinted))
                    isDotPrinted = true
                }
            })
            KeyboardButton(value = 0,
                onClick = { onValueChange(value.changeValue(it, isDotPrinted)) })
            KeyboardButton(value = DEL, onClick = {
                val newValue = value.changeValue(it, isDotPrinted)
                if (isDotPrinted) {
                    isDotPrinted = newValue.isHasRemain()
                }
                onValueChange(newValue)
            })
        }
    }
}

@Composable
private fun RowScope.KeyboardButton(value: Int, onClick: (Int) -> Unit) {
    Button(
        onClick = { onClick(value) },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.boxBackground,
            contentColor = MaterialTheme.colors.textPrimary),
        modifier = Modifier
            .padding(4.dp)
            .size(70.dp)
            .aspectRatio(1f, true)
    ) {
        AppText(text = when (value) {
            DOT -> '\u2022'.toString()
            DEL -> "X"
            else -> value.toString()
        }, modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun AppKeyboardPreview() {
    AppTheme {
        TransactionKeyboard(3012.25) {}
    }
}

private const val DOT = -1
private const val DEL = -2

private fun Double.changeValue(dif: Int, isDotPrinted: Boolean): Double {
    return try {
        val valueString = this.formatMoney()
        val base = valueString.split(".")[0]
        val remain = valueString.split(".")[1]
        var newValue = ""
        when (dif) {
            DOT -> {
                if (!isDotPrinted) newValue = "$base.$remain"
            }
            DEL -> {
                newValue = if (isDotPrinted) {
                    when {
                        remain.last() != '0' -> "$base.${remain.first()}0"
                        else -> "$base.00"
                    }
                } else {
                    if (base.isNotEmpty()) "${base.dropLast(1)}.$remain" else "0.$remain"
                }
            }
            else -> {
                newValue = if (isDotPrinted) {
                    when {
                        remain.first() == '0' -> "$base.${dif}0"
                        remain.last() == '0' -> "$base.${remain.first()}${dif}"
                        else -> "$base.$remain"
                    }
                } else {
                    "$base$dif.$remain"
                }
            }
        }
        newValue.toDouble()
    } catch (ex: Exception) {
        ex.printStackTrace()
        0.0
    }
}

private fun Double.isHasRemain(): Boolean {
    return try {
        this.formatMoney().split(".")[1] != "00"
    } catch (ex: Exception) {
        false
    }
}
