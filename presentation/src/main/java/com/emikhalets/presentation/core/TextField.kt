//package com.emikhalets.myfinances.presentation.core
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.LocalTextStyle
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.material.TextFieldDefaults
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Android
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.emikhalets.presentation.theme.AppTheme
//import com.emikhalets.presentation.theme.border
//import com.emikhalets.presentation.theme.borderFocused
//import com.emikhalets.presentation.theme.textError
//import com.emikhalets.presentation.theme.textFieldBackground
//import com.emikhalets.presentation.theme.textPrimary
//import com.emikhalets.presentation.theme.textSecondary
//
//@Composable
//fun AppTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    label: String? = null,
//    placeholder: String? = null,
//    leadingIcon: ImageVector? = null,
//    trailingIcon: ImageVector? = null,
//    error: String? = null,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//    singleLine: Boolean = false,
//    maxLines: Int = Int.MAX_VALUE,
//    onClick: () -> Unit = {},
//    keyboardType: KeyboardType = KeyboardType.Text,
//    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
//) {
//    Column(modifier = modifier.clickable { onClick() }) {
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            modifier = modifier,
//            enabled = enabled,
//            readOnly = readOnly,
//            label = getLabel(label),
//            placeholder = getPlaceholder(placeholder),
//            leadingIcon = getIcon(leadingIcon),
//            trailingIcon = getIcon(trailingIcon),
//            isError = !error.isNullOrEmpty(),
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions.copy(
//                keyboardType = keyboardType,
//                capitalization = capitalization
//            ),
//            keyboardActions = keyboardActions,
//            singleLine = singleLine,
//            maxLines = maxLines,
//            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = MaterialTheme.colors.textPrimary,
//                disabledTextColor = MaterialTheme.colors.textPrimary,
//                backgroundColor = MaterialTheme.colors.textFieldBackground,
//                cursorColor = MaterialTheme.colors.textPrimary,
//                errorCursorColor = MaterialTheme.colors.textPrimary,
//                focusedIndicatorColor = MaterialTheme.colors.borderFocused,
//                unfocusedIndicatorColor = MaterialTheme.colors.border,
//                disabledIndicatorColor = MaterialTheme.colors.border,
//                errorIndicatorColor = MaterialTheme.colors.error,
//                leadingIconColor = MaterialTheme.colors.textPrimary,
//                disabledLeadingIconColor = MaterialTheme.colors.textPrimary,
//                errorLeadingIconColor = MaterialTheme.colors.textError,
//                trailingIconColor = MaterialTheme.colors.textPrimary,
//                disabledTrailingIconColor = MaterialTheme.colors.textPrimary,
//                errorTrailingIconColor = MaterialTheme.colors.textError,
//                focusedLabelColor = MaterialTheme.colors.borderFocused,
//                unfocusedLabelColor = MaterialTheme.colors.border,
//                disabledLabelColor = MaterialTheme.colors.border,
//                errorLabelColor = MaterialTheme.colors.textError,
//                placeholderColor = MaterialTheme.colors.textSecondary,
//                disabledPlaceholderColor = MaterialTheme.colors.textSecondary
//            ),
//        )
//
//        if (!error.isNullOrEmpty()) {
//            TextError(
//                text = error,
//                size = 14.sp,
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//private fun getLabel(label: String?): @Composable (() -> Unit)? {
//    return if (label == null) {
//        null
//    } else {
//        { Text(text = label) }
//    }
//}
//
//@Composable
//private fun getPlaceholder(placeholder: String?): @Composable (() -> Unit)? {
//    return if (placeholder == null) {
//        null
//    } else {
//        { Text(text = placeholder) }
//    }
//}
//
//@Composable
//private fun getIcon(icon: ImageVector?): @Composable (() -> Unit)? {
//    return if (icon == null) {
//        null
//    } else {
//        { AppIcon(icon) }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldPreview() {
//    AppTheme {
//        AppTextField(
//            value = "",
//            onValueChange = {},
//            label = "Some label",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldValuePreview() {
//    AppTheme {
//        AppTextField(
//            value = "Some text",
//            onValueChange = {},
//            label = "Some label",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldPlaceholderPreview() {
//    AppTheme {
//        AppTextField(
//            value = "",
//            onValueChange = {},
//            label = "Some label",
//            placeholder = "Some placeholder",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldIconsPreview() {
//    AppTheme {
//        AppTextField(
//            value = "Some text",
//            onValueChange = {},
//            label = "Some label",
//            leadingIcon = Icons.Default.Android,
//            trailingIcon = Icons.Default.Android,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldErrorPreview() {
//    AppTheme {
//        AppTextField(
//            value = "Some text",
//            onValueChange = {},
//            label = "Some label",
//            error = "Some error",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextFieldIconsErrorPreview() {
//    AppTheme {
//        AppTextField(
//            value = "Some text",
//            onValueChange = {},
//            label = "Some label",
//            error = "Some error",
//            leadingIcon = Icons.Default.Android,
//            trailingIcon = Icons.Default.Android,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Deprecated("")
//@Composable
//fun AppTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    labelRes: Int? = null,
//    onClick: () -> Unit = {},
//    leadingIcon: Int? = null,
//    trailingIcon: Int? = null,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    singleLine: Boolean = true,
//    type: KeyboardType = KeyboardType.Text,
//    keyboardActions: KeyboardActions = KeyboardActions(),
//    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    padding: PaddingValues = PaddingValues(0.dp),
//    errorEmpty: Boolean = false,
//    errorInvalid: Boolean = false,
//    errorSelecting: Boolean = false,
//    fontSize: Int = 16,
//) {
//    val error = errorEmpty || errorInvalid || errorSelecting
//    val errorMessage = ""
//
//    val labelText: @Composable (() -> Unit)? = if (labelRes == null) {
//        null
//    } else {
//        { Text(text = stringResource(labelRes), color = MaterialTheme.colors.onPrimary) }
//    }
//
//    Box(modifier.padding(padding)) {
//        TextField(
//            value = value,
//            onValueChange = onValueChange,
//            label = labelText,
//            leadingIcon = if (leadingIcon != null) {
//                { AppIcon(drawable = leadingIcon) }
//            } else {
//                null
//            },
//            trailingIcon = if (trailingIcon != null) {
//                { AppIcon(drawable = trailingIcon) }
//            } else {
//                null
//            },
//            textStyle = MaterialTheme.typography.body1.copy(
//                fontSize = fontSize.sp
//            ),
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = MaterialTheme.colors.onPrimary,
//                disabledTextColor = MaterialTheme.colors.onPrimary,
//                backgroundColor = MaterialTheme.colors.background,
//                focusedIndicatorColor = MaterialTheme.colors.primary,
//                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
//                errorIndicatorColor = MaterialTheme.colors.error,
//                disabledIndicatorColor = MaterialTheme.colors.secondary,
//                leadingIconColor = MaterialTheme.colors.onPrimary,
//                disabledLeadingIconColor = MaterialTheme.colors.onPrimary,
//                trailingIconColor = MaterialTheme.colors.onPrimary,
//                disabledTrailingIconColor = MaterialTheme.colors.onPrimary,
//                errorLabelColor = MaterialTheme.colors.error,
//                errorTrailingIconColor = MaterialTheme.colors.onPrimary
//            ),
//            enabled = enabled,
//            readOnly = readOnly,
//            singleLine = singleLine,
//            maxLines = Int.MAX_VALUE,
//            isError = error,
//            keyboardActions = keyboardActions,
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = type,
//                capitalization = capitalization
//            ),
//            visualTransformation = visualTransformation,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onClick() }
//        )
//        if (errorEmpty || errorInvalid || errorSelecting) {
//            Text(
//                text = errorMessage,
//                color = Color.Red,
//                textAlign = TextAlign.End,
//                style = MaterialTheme.typography.caption,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp, end = 16.dp)
//            )
//        }
//    }
//}