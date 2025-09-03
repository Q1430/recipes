package com.recipe.recipes.presentation.discovery

sealed class DisScreen(val route: String){
    object Find : DisScreen("find_screen")

    object GetRandom : DisScreen("random_screen")

    object MealDetail : DisScreen("detail_screen/{mealId}"){
        fun createRoute(mealId: String) = "detail_screen/$mealId"
    }
}
