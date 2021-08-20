package com.emikhalets.myfinances.ui.screens.dialogs

import AppDialogCustom
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.base.AppVerticalList
import com.emikhalets.myfinances.ui.base.ButtonAdd
import com.emikhalets.myfinances.ui.base.TextWithIcon
import com.emikhalets.myfinances.utils.enums.MyIcons

@Composable
fun <T> ListChooserDialog(
    buttonText: String,
    items: List<T>,
    onDismiss: () -> Unit,
    onSelect: (T) -> Unit,
    onAddClick: () -> Unit,
) {
    AppDialogCustom(onDismiss = { onDismiss() }) {
        if (items.isEmpty()) TextWithIcon(
            text = buttonText,
            icon = MyIcons.Plus.icon,
            onClick = { onAddClick() }
        )
        else {
            AppVerticalList(
                list = items,
                padding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) { item ->
                var name = ""
                when (item) {
                    is Wallet -> name = item.name
                    is Category -> name = item.name
                }
                TextWithIcon(
                    text = name,
                    icon = MyIcons.Wallet.icon,
                    onClick = { onSelect(item) }
                )
                if (item == items.last()) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    ButtonAdd(
                        text = buttonText,
                        onClick = onAddClick
                    )
                }
            }
        }
    }
}