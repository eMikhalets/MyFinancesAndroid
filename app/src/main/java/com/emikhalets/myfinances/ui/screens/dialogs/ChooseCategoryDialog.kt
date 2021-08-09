package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category

@Composable
fun ChooseCategoryDialog(
    categories: List<Category>,
    onSelect: (Category) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            LazyColumn(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                items(categories) { category ->
                    ChooseCategoryButton(
                        label = category.name,
                        icon = painterResource(category.icon),
                        modifier = Modifier.clickable {
                            onSelect(category)
                            onDismiss(false)
                        }
                    )
                    if (category == categories.last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        ChooseCategoryButton(
                            label = stringResource(id = R.string.new_category),
                            icon = painterResource(R.drawable.ic_add),
                            modifier = Modifier.clickable {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseCategoryButton(
    label: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
    }
}