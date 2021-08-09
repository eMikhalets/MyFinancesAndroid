package com.emikhalets.myfinances.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.emikhalets.myfinances.R

enum class CategoryIcon(val value: String) {
    TEST("test");

    companion object {
        private val map = values().associateBy(CategoryIcon::value)
        fun get(value: String): CategoryIcon = map[value] ?: TEST

        @Composable
        fun CategoryIcon.getImageVector(): Painter {
            return when (this) {
                TEST -> painterResource(R.drawable.ic_attach_money)
            }
        }
    }
}