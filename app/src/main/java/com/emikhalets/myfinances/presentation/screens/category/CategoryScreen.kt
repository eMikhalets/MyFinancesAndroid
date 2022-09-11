package com.emikhalets.myfinances.presentation.screens.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.AppToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.enums.TransactionType.Companion.getName
import com.emikhalets.myfinances.utils.toast

@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryId: Long,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.getCategory(categoryId) }

    LaunchedEffect(state.category) {
        if (state.category != null) {
            name = state.category.name
        }
    }

    LaunchedEffect(state.categorySaved) {
        if (state.categorySaved) navController.popBackStack()
    }

    LaunchedEffect(state.categoryDeleted) {
        if (state.categoryDeleted) navController.popBackStack()
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    CategoryScreen(
        navController = navController,
        category = state.category,
        name = name,
        value = 0.0,
        onNameChange = { name = it },
        onSaveClick = { viewModel.saveCategory(name) },
        onDeleteClick = viewModel::deleteCategory
    )
}

@Composable
private fun CategoryScreen(
    navController: NavHostController,
    category: Category?,
    name: String,
    value: Double,
    onNameChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    category ?: return
    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_category_screen))
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TypeText(category.type)
            Spacer(Modifier.height(16.dp))

            AppTextField(name, onNameChange, labelRes = R.string.label_name)
            Spacer(Modifier.height(16.dp))

            ValueText(value)
            Spacer(Modifier.height(16.dp))

            ControlButtons(onSaveClick, onDeleteClick)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TypeText(type: TransactionType) {
    AppText(
        text = stringResource(R.string.transaction_type_header, type.getName()),
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
private fun ValueText(value: Double) {
    AppText(
        text = stringResource(R.string.app_money_value, value),
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun ControlButtons(onSaveClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.delete),
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f)
        )
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        CategoryScreen(
            navController = rememberNavController(),
            category = Category("Preview category name", TransactionType.Income),
            name = "Preview category name",
            value = 656565.65,
            onNameChange = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}