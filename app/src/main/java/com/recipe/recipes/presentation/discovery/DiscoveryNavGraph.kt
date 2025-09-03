package com.recipe.recipes.presentation.discovery

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.recipe.recipes.presentation.favorite.FavScreen
import com.recipe.recipes.presentation.favorites.FavoritesScreen

@Composable
fun NavGraphD(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = DisScreen.Find.route
    ){
        //收藏
        composable(route = FavScreen.Favorite.route) {
            FavoritesScreen(navController)
        }
        //发现
        composable(route = DisScreen.Find.route) {
            DiscoveryFindScreen(navController)
        }
        composable(route = DisScreen.GetRandom.route) {
            DiscoveryGetRandomScreen(navController)
        }
        composable(
            route = DisScreen.MealDetail.route,
            arguments = listOf(navArgument("mealId"){type = NavType.StringType})
        ) {backStackEntry ->
            // 从导航回退栈条目中安全地获取 mealId 参数
            val mealId = backStackEntry.arguments?.getString("mealId")
            // 确保 mealId 不为空，再加载详情页
            if (mealId != null) {
                MealDetailScreen(
                    mealId = mealId,
                    navController = navController
                )
            }
        }

    }

}
