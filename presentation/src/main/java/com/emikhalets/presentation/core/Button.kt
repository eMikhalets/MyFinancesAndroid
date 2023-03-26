package com.emikhalets.presentation.core

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text)
    }
}

@Composable
fun AppTextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text)
    }
}

//@Composable
//fun AppButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
//    Button(
//        onClick = { onClick() },
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = MaterialTheme.colors.boxBackground,
//            contentColor = MaterialTheme.colors.textPrimary
//        ),
//        modifier = modifier
//    ) {
//        AppText(
//            text = text,
//            modifier = Modifier.padding(8.dp)
//        )
//    }
//}
//
//@Composable
//fun AppTextButton(
//    text: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    textColor: Color = MaterialTheme.colors.textButton,
//    padding: PaddingValues = PaddingValues(8.dp),
//) {
//    TextButton(
//        onClick = { onClick() },
//        modifier = modifier
//    ) {
//        AppText(
//            text = text,
//            fontColor = textColor,
//            modifier = Modifier.padding(padding)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppTextButtonPreview() {
//    AppTheme {
//        AppTextButton(
//            text = "Some text",
//            onClick = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun AppButtonPreview() {
//    AppTheme {
//        AppButton(
//            text = "Some text",
//            onClick = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//    }
//}