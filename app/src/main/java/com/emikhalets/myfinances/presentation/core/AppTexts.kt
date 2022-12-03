package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.presentation.theme.textError
import com.emikhalets.myfinances.presentation.theme.textPrimary
import com.emikhalets.myfinances.presentation.theme.textSecondary

@Composable
fun TextPrimary(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.textPrimary,
        fontSize = size,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
fun TextPrimaryFillWidth(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    TextPrimary(
        text = text,
        size = size,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun TextSecondary(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.textSecondary,
        fontSize = size,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
fun TextError(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.textError,
        fontSize = size,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    fontColor: Color = MaterialTheme.colors.onSurface,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    fontStyle: FontStyle? = MaterialTheme.typography.body1.fontStyle,
    fontWeight: FontWeight? = MaterialTheme.typography.body1.fontWeight,
    fontFamily: FontFamily? = MaterialTheme.typography.body1.fontFamily,
    letterSpacing: TextUnit = MaterialTheme.typography.body1.letterSpacing,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    lineHeight: TextUnit = MaterialTheme.typography.body1.lineHeight,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = fontColor,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        maxLines = maxLines
    )
}