package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.myfinances.R

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    type: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(),
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    padding: PaddingValues = PaddingValues(0.dp),
    errorEmpty: Boolean = false,
    errorInvalid: Boolean = false,
    errorSelecting: Boolean = false,
    fontSize: Int = 16,
) {
    val error = errorEmpty || errorInvalid || errorSelecting
    val errorMessage = ""

    val labelText: @Composable (() -> Unit)? = if (label == null) {
        null
    } else {
        { Text(text = label, color = MaterialTheme.colors.onPrimary) }
    }

    Box(modifier.padding(padding)) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = labelText,
            leadingIcon = if (leadingIcon != null) {
                { AppIcon(drawable = leadingIcon) }
            } else {
                null
            },
            trailingIcon = if (trailingIcon != null) {
                { AppIcon(drawable = trailingIcon) }
            } else {
                null
            },
            textStyle = MaterialTheme.typography.body1.copy(
                fontSize = fontSize.sp
            ),
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
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = type,
                capitalization = capitalization
            ),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
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

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelRes: Int? = null,
    onClick: () -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    type: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(),
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    padding: PaddingValues = PaddingValues(0.dp),
    errorEmpty: Boolean = false,
    errorInvalid: Boolean = false,
    errorSelecting: Boolean = false,
    fontSize: Int = 16,
) {
    val error = errorEmpty || errorInvalid || errorSelecting
    val errorMessage = ""

    val labelText: @Composable (() -> Unit)? = if (labelRes == null) {
        null
    } else {
        { Text(text = stringResource(labelRes), color = MaterialTheme.colors.onPrimary) }
    }

    Box(modifier.padding(padding)) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = labelText,
            leadingIcon = if (leadingIcon != null) {
                { AppIcon(drawable = leadingIcon) }
            } else {
                null
            },
            trailingIcon = if (trailingIcon != null) {
                { AppIcon(drawable = trailingIcon) }
            } else {
                null
            },
            textStyle = MaterialTheme.typography.body1.copy(
                fontSize = fontSize.sp
            ),
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
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = type,
                capitalization = capitalization
            ),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
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