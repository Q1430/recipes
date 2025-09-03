package com.recipe.recipes.util

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}