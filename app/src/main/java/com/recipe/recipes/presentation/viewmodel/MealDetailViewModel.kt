package com.recipe.recipes.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.recipes.domain.use_case.DeleteFavoriteUseCase
import com.recipe.recipes.domain.use_case.GetMealDetailsByIdUseCase
import com.recipe.recipes.domain.use_case.IsFavoriteUseCase
import com.recipe.recipes.domain.use_case.SaveFavoriteMealUseCase
import com.recipe.recipes.presentation.state.MealState
import com.recipe.recipes.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getMealDetailsByIdUseCase: GetMealDetailsByIdUseCase,
    private val saveFavoriteMealUseCase: SaveFavoriteMealUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val isMealFavoriteUseCase: IsFavoriteUseCase,
    // SavedStateHandle 是 Hilt 提供的，用于安全地访问导航参数
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealState())
    val state: StateFlow<MealState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // ViewModel 初始化时，从 SavedStateHandle 中获取 mealId
        // "mealId" 这个键必须和你在 NavHost 中定义的参数名完全一致
        savedStateHandle.get<String>("mealId")?.let { mealId ->
            // 启动时，同时执行两个任务：获取菜谱详情 和 监听收藏状态
            getMealDetails(mealId)
            observeFavoriteStatus(mealId)
        }
    }

    /**
     * UI 可以调用的公共事件，用于切换收藏状态
     */
    fun onFavoriteToggle() {
        viewModelScope.launch {
            // 只有在 meal 数据已加载的情况下才执行
            _state.value.meal?.let { meal -> try {
                if (_state.value.isFavorite) {
                    deleteFavoriteUseCase(meal.id)
                    _eventFlow.emit(UiEvent.ShowSnackBar("已取消收藏"))
                } else {
                    saveFavoriteMealUseCase(meal)
                    _eventFlow.emit(UiEvent.ShowSnackBar("收藏成功！"))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackBar("操作失败: ${e.localizedMessage}"))
            }
            }
        }
    }

    /**
     * 根据 ID 获取菜谱的详细信息
     */
    private fun getMealDetails(mealId: String) {
        // 在调用前，可以先更新状态为加载中
        _state.update { it.copy(isLoading = true) }

        getMealDetailsByIdUseCase(mealId)
            .onEach { result ->
                result
                    .onSuccess { meal ->
                        _state.update { it.copy(isLoading = false, meal = meal, error = null) }
                    }
                    .onFailure { exception ->
                        _state.update { it.copy(isLoading = false, error = exception.message) }
                    }
            }
            .launchIn(viewModelScope)
    }
    /**
     * 持续监听收藏状态
     */
    private fun observeFavoriteStatus(mealId: String){
        isMealFavoriteUseCase(mealId)
            .onEach{ isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
            .launchIn(viewModelScope)
    }

}