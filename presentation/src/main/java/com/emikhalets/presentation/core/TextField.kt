package com.emikhalets.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.border
import com.emikhalets.presentation.theme.borderFocused
import com.emikhalets.presentation.theme.textError
import com.emikhalets.presentation.theme.textFieldBackground
import com.emikhalets.presentation.theme.textPrimary
import com.emikhalets.presentation.theme.textSecondary

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    onClick: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
) {
    Column(modifier = modifier.clickable { onClick() }) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            label = if (label != null) {
                { Text(text = label) }
            } else null,
            placeholder = if (placeholder != null) {
                { Text(text = placeholder) }
            } else null,
            leadingIcon = if (leadingIcon != null) {
                { Icon(imageVector = leadingIcon, contentDescription = null) }
            } else null,
            trailingIcon = if (trailingIcon != null) {
                { Icon(imageVector = trailingIcon, contentDescription = null) }
            } else null,
            isError = !error.isNullOrEmpty(),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions.copy(
                keyboardType = keyboardType,
                capitalization = capitalization
            ),
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.textPrimary,
                disabledTextColor = MaterialTheme.colors.textPrimary,
                backgroundColor = MaterialTheme.colors.textFieldBackground,
                cursorColor = MaterialTheme.colors.textPrimary,
                errorCursorColor = MaterialTheme.colors.textPrimary,
                focusedIndicatorColor = MaterialTheme.colors.borderFocused,
                unfocusedIndicatorColor = MaterialTheme.colors.border,
                disabledIndicatorColor = MaterialTheme.colors.border,
                errorIndicatorColor = MaterialTheme.colors.error,
                leadingIconColor = MaterialTheme.colors.textPrimary,
                disabledLeadingIconColor = MaterialTheme.colors.textPrimary,
                errorLeadingIconColor = MaterialTheme.colors.textError,
                trailingIconColor = MaterialTheme.colors.textPrimary,
                disabledTrailingIconColor = MaterialTheme.colors.textPrimary,
                errorTrailingIconColor = MaterialTheme.colors.textError,
                focusedLabelColor = MaterialTheme.colors.borderFocused,
                unfocusedLabelColor = MaterialTheme.colors.border,
                disabledLabelColor = MaterialTheme.colors.border,
                errorLabelColor = MaterialTheme.colors.textError,
                placeholderColor = MaterialTheme.colors.textSecondary,
                disabledPlaceholderColor = MaterialTheme.colors.textSecondary
            ),
        )

        if (!error.isNullOrEmpty()) {
            Text(
                text = error,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            label = "Some label",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldValuePreview() {
    AppTheme {
        AppTextField(
            value = "Some text",
            onValueChange = {},
            label = "Some label",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPlaceholderPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            label = "Some label",
            placeholder = "Some placeholder",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldIconsPreview() {
    AppTheme {
        AppTextField(
            value = "Some text",
            onValueChange = {},
            label = "Some label",
            leadingIcon = Icons.Default.Android,
            trailingIcon = Icons.Default.Android,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldErrorPreview() {
    AppTheme {
        AppTextField(
            value = "Some text",
            onValueChange = {},
            label = "Some label",
            error = "Some error",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldIconsErrorPreview() {
    AppTheme {
        AppTextField(
            value = "Some text",
            onValueChange = {},
            label = "Some label",
            error = "Some error",
            leadingIcon = Icons.Default.Android,
            trailingIcon = Icons.Default.Android,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}