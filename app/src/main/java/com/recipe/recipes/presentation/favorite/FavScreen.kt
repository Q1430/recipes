package com.recipe.recipes.presentation.favorite

import okhttp3.Route

sealed class FavScreen(val route: String) {
    object Favorite: FavScreen("favorite_screen")

    object MealDetail: FavScreen("detail_screen/{mealId}"){
        fun createRoute(mealId: String) = "detail_screen/$mealId"
    }
}