package com.recipe.recipes.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}