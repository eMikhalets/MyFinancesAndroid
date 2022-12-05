package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.domain.entity.Category

@Composable
fun AppCategorySpinner(
    categories: List<Category>,
    initItem: Category? = null,
    onSelect: (Category) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initItem ?: categories.first()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedTextField(
            value = selectedItem.name,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            trailingIcon = {
                if (expanded) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                val name = if (category.hasDefaultId()) {
                    stringResource(id = R.string.app_no_category)
                } else {
                    category.name
                }
                DropdownMenuItem(
                    onClick = {
                        selectedItem = category
                        expanded = false
                        onSelect(category)
                    },
                    content = { Text(text = name) }
                )
            }
        }
    }
}