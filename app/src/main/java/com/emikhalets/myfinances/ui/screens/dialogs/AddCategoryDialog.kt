package com.emikhalets.myfinances.ui.screens.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppDialogCustom
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.utils.enums.AppIcon

@Composable
fun AddCategoryDialog(
    onSave: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf(-1) }
    var nameError by remember { mutableStateOf(false) }
    var iconError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.new_category),
        onDismiss = { onDismiss() }
    ) {
        IconsList(
            iconError = iconError,
            onSelectIcon = {
                icon = it
                iconError = false
            }
        )
        Spacer(Modifier.height(8.dp))
        AppTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            leadingIcon = AppIcon.Pencil.icon,
            label = stringResource(R.string.name),
            errorEmpty = nameError
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when {
                    name.isEmpty() -> nameError = true
                    icon < 0 -> iconError = true
                    else -> onSave(name, icon)
                }
            }
        )
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconsList(
    iconError: Boolean,
    onSelectIcon: (Int) -> Unit
) {
    var selected by remember { mutableStateOf(-1) }
    val icons = listOf(
        AppIcon.DotsHollow,
        AppIcon.Cutlery,
        AppIcon.Gift,
        AppIcon.Gym,
        AppIcon.Healthcare,
        AppIcon.Loan,
        AppIcon.Mortarboard,
        AppIcon.Tent,
        AppIcon.Train,
        AppIcon.Tshirt,
        AppIcon.User,
        AppIcon.Wifi,
        AppIcon.Salary,
        AppIcon.SaveMoney
    )

    LazyVerticalGrid(
        cells = GridCells.Adaptive(60.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 4.dp,
                shape = RoundedCornerShape(8.dp),
                color = if (iconError) {
                    MaterialTheme.colors.error
                } else {
                    Color.Transparent
                }
            )
    ) {
        items(icons) { icon ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        selected = icon.id
                        onSelectIcon(icon.id)
                    }
                    .background(
                        color = if (selected == icon.id) {
                            MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        } else {
                            MaterialTheme.colors.surface
                        },
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(icon.icon),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
    }
}