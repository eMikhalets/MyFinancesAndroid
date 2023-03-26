package com.emikhalets.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
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
import com.emikhalets.domain.entity.CategoryEntity

@Composable
fun AppCategoriesSpinner(
    categoriesList: List<CategoryEntity>,
    selectedCategory: CategoryEntity,
    onSelectCategory: (CategoryEntity) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedCategory.name,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        if (expanded) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
        } else {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categoriesList.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelectCategory(category)
                    },
                    content = { Text(text = category.name) }
                )
            }
        }
    }
}