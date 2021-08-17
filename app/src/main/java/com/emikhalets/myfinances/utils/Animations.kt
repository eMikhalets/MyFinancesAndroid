package com.emikhalets.myfinances.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateFadeInOut(
    visible: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = duration)),
        exit = fadeOut(animationSpec = tween(durationMillis = duration))
    ) {
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateExpandCollapse(
    visible: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(animationSpec = tween(durationMillis = duration)),
        exit = shrinkVertically(animationSpec = tween(durationMillis = duration))
    ) {
        content()
    }
}