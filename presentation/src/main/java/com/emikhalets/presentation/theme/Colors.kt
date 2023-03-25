package com.emikhalets.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Blue_400 = Color(0xFF42A5F5)
val Blue_700 = Color(0xFF1976D2)
val Red_700 = Color(0xFFD32F2F)
val Grey_500 = Color(0xFF9E9E9E)
val Grey_700 = Color(0xFF616161)
val Grey_800 = Color(0xFF424242)

val Colors.textPrimary
    get() = if (isLight) Color.Black else Color.White

val Colors.textSecondary
    get() = if (isLight) Grey_500 else Color.White

val Colors.textButton
    get() = if (isLight) Blue_700 else Color.White

val Colors.textError
    get() = if (isLight) Red_700 else Red_700

val Colors.appBackground
    get() = if (isLight) Color.White else Color.Black

val Colors.boxBackground
    get() = if (isLight) Blue_400 else Color.Black

val Colors.textFieldBackground
    get() = if (isLight) Color.White else Color.Black

val Colors.border
    get() = if (isLight) Grey_700 else Color.Black

val Colors.borderFocused
    get() = if (isLight) Grey_800 else Color.Black