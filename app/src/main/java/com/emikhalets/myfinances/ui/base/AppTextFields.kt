package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R

@Composable
fun AppTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    type: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorEmpty: Boolean = false,
    errorInvalid: Boolean = false,
    errorSelecting: Boolean = false
) {
    val error = errorEmpty || errorInvalid || errorSelecting
    val errorMessage: String = when {
        errorEmpty -> stringResource(R.string.error_required_field)
        errorInvalid -> stringResource(R.string.error_invalid_value)
        errorSelecting -> stringResource(R.string.error_selecting)
        else -> stringResource(R.string.error)
    }

    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            leadingIcon = if (leadingIcon != null) {
                { Icon(painter = painterResource(leadingIcon), contentDescription = "") }
            } else null,
            trailingIcon = if (trailingIcon != null) {
                { Icon(painter = painterResource(trailingIcon), contentDescription = "") }
            } else null,
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                disabledTextColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                errorIndicatorColor = MaterialTheme.colors.error,
                disabledIndicatorColor = MaterialTheme.colors.secondary,
                leadingIconColor = MaterialTheme.colors.onPrimary,
                disabledLeadingIconColor = MaterialTheme.colors.onPrimary,
                trailingIconColor = MaterialTheme.colors.onPrimary,
                disabledTrailingIconColor = MaterialTheme.colors.onPrimary,
                errorLabelColor = MaterialTheme.colors.error,
                errorTrailingIconColor = MaterialTheme.colors.onPrimary
            ),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            maxLines = Int.MAX_VALUE,
            isError = error,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = type,
                capitalization = capitalization
            ),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .clickable { onClick() }
        )
        if (errorEmpty || errorInvalid || errorSelecting) {
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 16.dp)
            )
        }
    }
}