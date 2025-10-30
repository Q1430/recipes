package com.recipe.recipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController


//获取绑定到根导航图的全局共享ViewModel
@Composable
inline fun <reified T:ViewModel> NavController.getAppViewModel(
    backStackEntry: NavBackStackEntry
):T{
    val rootEntry = remember(backStackEntry){
        this.getBackStackEntry(Routes.ROOT_GRAPH)
    }
    return hiltViewModel<T>(rootEntry)
}

@Composable
inline fun <reified T : ViewModel> NavController.getSharedViewModel(
    route: String,
    backStackEntry:NavBackStackEntry
): T {
    val parentEntry = remember(backStackEntry) {
        this.getBackStackEntry(route)
    }
    return hiltViewModel<T>(parentEntry)
}