//package com.emikhalets.myfinances.presentation.core
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Scaffold
//import androidx.compose.material.TopAppBar
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.emikhalets.myfinances.R
//import com.emikhalets.presentation.navigation.Screen
//import com.emikhalets.presentation.navigation.Screen.Companion.isRootScreen
//import com.emikhalets.presentation.theme.boxBackground
//
//@Composable
//fun AppScaffold(
//    navController: NavHostController,
//    title: String = "",
//    content: @Composable () -> Unit,
//) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = Screen.getScreen(navBackStackEntry)
//    val isRootScreen = currentScreen.isRootScreen()
//    val toolbarTitle = title.ifEmpty {
//        stringResource(currentScreen?.title ?: R.string.app_name_title)
//    }
//
//    Scaffold(
//        topBar = { AppToolbar(navController, toolbarTitle, isRootScreen) },
//        content = { Box(Modifier.padding(it)) { content() } }
//    )
//}
//
//@Composable
//private fun AppToolbar(navController: NavHostController, title: String, isRootScreen: Boolean) {
//    TopAppBar(
//        title = { TextPrimary(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
//        elevation = 0.dp,
//        backgroundColor = MaterialTheme.colors.boxBackground,
//        navigationIcon = if (!isRootScreen) {
//            {
//                AppIcon(
//                    imageVector = Icons.Rounded.ArrowBack,
//                    modifier = Modifier
//                        .clickable { navController.popBackStack() }
//                        .padding(20.dp, 16.dp)
//                )
//            }
//        } else {
//            null
//        }
//    )
//}