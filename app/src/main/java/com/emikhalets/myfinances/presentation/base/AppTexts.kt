package com.emikhalets.myfinances.presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.toValue

@Composable
fun TextFullScreen(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AppText(
            text = text,
            fontColor = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun TextWithIcon(
    text: String,
    icon: Int,
    onClick: () -> Unit = {},
    iconSize: Dp = 24.dp,
    padding: PaddingValues = PaddingValues(16.dp),
    color: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(padding)
            .fillMaxWidth()
    ) {
        AppIcon(
            drawable = icon,
            size = iconSize
        )
        Spacer(Modifier.width(16.dp))
        AppText(
            text = text,
            fontColor = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun AppTextMoney(
    value: Double,
    modifier: Modifier = Modifier,
    type: TransactionType? = null,
    fontSize: TextUnit = 18.sp,
    fontColor: Color = MaterialTheme.colors.onSurface
) {
    val icon = when (type) {
        TransactionType.Expense -> R.drawable.ic_minus
        TransactionType.Income -> R.drawable.ic_plus
        else -> null
    }

    AppTextWithIcon(
        text = value.toValue(),
        drawable = icon,
        drawablePadding = 16.dp,
        drawableSize = 18.dp,
        fontSize = fontSize,
        fontColor = fontColor,
        modifier = modifier
    )
}

@Composable
fun AppTextWithIcon(
    text: String,
    modifier: Modifier = Modifier,
    drawable: Int? = null,
    drawablePadding: Dp = 0.dp,
    drawableColor: Color = MaterialTheme.colors.onSurface,
    drawableSize: Dp = 24.dp,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    fontColor: Color = MaterialTheme.colors.onSurface,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if (drawable != null) {
            AppIcon(
                drawable = drawable,
                color = drawableColor,
                size = drawableSize
            )
            Spacer(modifier = Modifier.width(drawablePadding))
        }
        AppText(
            text = text,
            fontSize = fontSize,
            fontColor = fontColor
        )
    }
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
    maxLines: Int = Int.MAX_VALUE
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