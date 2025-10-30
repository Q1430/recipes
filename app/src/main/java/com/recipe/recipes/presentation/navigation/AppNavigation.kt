package com.recipe.recipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.recipe.recipes.presentation.discovery.DiscoveryFindScreen
import com.recipe.recipes.presentation.discovery.MealDetailScreen
import com.recipe.recipes.presentation.favorites.FavoritesScreen
import com.recipe.recipes.presentation.search.SearchScreen
import com.recipe.recipes.presentation.viewmodel.DiscoveryViewModel
import com.recipe.recipes.presentation.viewmodel.FavoritesViewModel
import com.recipe.recipes.presentation.viewmodel.MealDetailViewModel
import com.recipe.recipes.presentation.viewmodel.SearchMealsViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController:NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_GRAPH,
        route = Routes.ROOT_GRAPH
    ){
        navigation(
            startDestination = Routes.FIND_GRAPH,
            route = Routes.MAIN_GRAPH
        ){
            findGraph(navController)

            composable(route = Routes.FAVORITE_SCREEN){backStackEntry ->
                val favoritesViewModel = navController.getAppViewModel<FavoritesViewModel>(backStackEntry)
                FavoritesScreen(
                    navController,
                    viewModel = favoritesViewModel
                )
            }

            composable(route = Routes.SEARCH_SCREEN){ backStackEntry ->
                val favoritesViewModel = navController.getAppViewModel<FavoritesViewModel>(backStackEntry)
                val searchMealsViewModel = navController.getSharedViewModel<SearchMealsViewModel>(
                    route = Routes.SEARCH_SCREEN,
                    backStackEntry = backStackEntry)
                SearchScreen(
                    navController,
                    searchMealsViewModel = searchMealsViewModel,
                    favoritesViewModel = favoritesViewModel
                )
            }
        }
        composable(
            route = Routes.DETAIL_SCREEN,
            arguments = listOf(navArgument("mealId"){type = NavType.StringType})
        ){backStackEntry ->
            val mealDetailViewModel  = navController.getSharedViewModel<MealDetailViewModel>(
                route = Routes.DETAIL_SCREEN,
                backStackEntry = backStackEntry)
            val mealId = backStackEntry.arguments?.getString("mealId")
            if (mealId != null) {
                MealDetailScreen(
                    mealId = mealId,
                    navController = navController,
                    mealDetailViewModel = mealDetailViewModel,
                )
            }
        }
    }
}