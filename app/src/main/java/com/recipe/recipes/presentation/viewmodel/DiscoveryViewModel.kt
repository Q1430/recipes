package com.recipe.recipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.recipes.domain.use_case.GetRandomMealUseCase
import com.recipe.recipes.presentation.state.DiscoveryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val getRandomMealUseCase: GetRandomMealUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(DiscoveryState())
    val state: StateFlow<DiscoveryState> = _state.asStateFlow()

    init {
        onGetRandomMeal()
    }

    fun onGetRandomMeal() {
        getRandomMealUseCase().onEach { result ->
            // 关键点2：使用 onSuccess 和 onFailure 链式调用来处理 Result
            result
                .onSuccess { meal ->
                    _state.update { it.copy(isLoading = false, randomMeal = meal, error = null) }
                }
                .onFailure { exception ->
                    _state.update { it.copy(isLoading = false, error = exception.message) }
                }
        }.launchIn(viewModelScope)
    }
}