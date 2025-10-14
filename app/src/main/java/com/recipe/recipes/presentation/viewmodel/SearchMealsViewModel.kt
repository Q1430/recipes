package com.recipe.recipes.presentation.viewmodel

import com.recipe.recipes.domain.use_case.GetAllAreaUseCase
import com.recipe.recipes.domain.use_case.GetMealByAreaUseCase
import com.recipe.recipes.domain.use_case.GetMealByCategoryUseCase
import com.recipe.recipes.domain.use_case.GetMealByIngredientUseCase
import com.recipe.recipes.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchMealsViewModel @Inject constructor(
    private val getMealByAreaUseCase: GetMealByAreaUseCase,
    private val getMealByIngredientUseCase: GetMealByIngredientUseCase,
    private val getMealByCategoryUseCase: GetMealByCategoryUseCase
) {
    private val _state = MutableStateFlow(SearchState())
    val state :StateFlow<SearchState> = _state.asStateFlow()


}