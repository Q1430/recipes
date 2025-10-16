package com.recipe.recipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.recipes.domain.use_case.DeleteFavoriteUseCase
import com.recipe.recipes.domain.use_case.GetFavoriteMealsUseCase
import com.recipe.recipes.presentation.state.FavoriteState
import com.recipe.recipes.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMealsUseCase: GetFavoriteMealsUseCase,
    private val deleteFavoriteMealUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    // 这个私有的 StateFlow 只用来管理当前的排序选择，默认是降序
    private val _orderType = MutableStateFlow<OrderType>(OrderType.Descending)

    // 这是最核心的响应式逻辑：
    // state 这个公开的状态流，它的内容是由 _orderType 的值动态决定的。
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<FavoriteState> = _orderType
        .flatMapLatest { orderType -> // 当 _orderType 变化时，这里会自动重新执行
            // 根据新的排序方式，调用 Use Case 获取新的、排好序的数据流
            getFavoriteMealsUseCase(orderType).map { meals ->
                // 将结果包装成一个完整的 UI State 对象
                FavoriteState(favoriteMeals = meals, orderType = orderType)
            }
        }
        .stateIn(
            scope = viewModelScope, // 在 ViewModel 的生命周期内管理
            started = SharingStarted.WhileSubscribed(5000L), // UI在前台时启动，离开5秒后停止
            initialValue = FavoriteState() // 提供一个初始的空状态
        )

    /**
     * 由 UI 调用，用于改变当前的排序顺序
     */
    fun onSortOrderChange(orderType: OrderType) {
        _orderType.value = orderType
    }

    /**
     * 由 UI 调用，用于取消收藏一个菜谱
     */
    fun onFavoriteClicked(mealId: String) {
        viewModelScope.launch {
            deleteFavoriteMealUseCase(mealId)
        }
    }
}