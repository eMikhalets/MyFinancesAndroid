package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.utils.toValue

@Composable
fun AppTextScreenTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = Color.Transparent,
    padding: PaddingValues = PaddingValues(16.dp)
) {
    Box(modifier = modifier.background(backgroundColor)) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        )
    }
}

@Composable
fun AppTextFillScreen(
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
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.body1.copy(
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        )
    }
}

@Composable
fun AppTextWithIcon(
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
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.body1.copy(
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        )
    }
}

@Composable
fun AppTextWithIcon(
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit = {},
    iconSize: Dp = 24.dp,
    padding: PaddingValues = PaddingValues(16.dp),
    color: Color = MaterialTheme.colors.onSurface,
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
        Icon(
            painter = iconPainter,
            contentDescription = "",
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.body1.copy(
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        )
    }
}

@Composable
fun TextValue(
    value: Double,
    color: Color = MaterialTheme.colors.onPrimary
) {
    AppText(
        text = value.toValue(),
        textAlign = TextAlign.End,
        fontSize = 20.sp,
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
    )
}

@Composable
fun TextCenter(
    text: String,
    color: Color = MaterialTheme.colors.onPrimary
) {
    AppText(
        text = text,
        textAlign = TextAlign.Center,
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize
) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1.copy(
            fontSize = fontSize
        ),
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}