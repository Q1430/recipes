package com.recipe.recipes.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.use_case.GetAllAreaUseCase
import com.recipe.recipes.domain.use_case.GetAllCategoryUseCase
import com.recipe.recipes.domain.use_case.GetAllIngredientUseCase
import com.recipe.recipes.domain.use_case.GetMealByFilterUseCase
import com.recipe.recipes.domain.use_case.GetMealByNameUseCase
import com.recipe.recipes.domain.use_case.MealFilter
import com.recipe.recipes.presentation.state.FilterType
import com.recipe.recipes.presentation.state.SearchState
import com.recipe.recipes.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMealsViewModel @Inject constructor(
    private val getAllAreaUseCase: GetAllAreaUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getAllIngredientUseCase: GetAllIngredientUseCase,
    private val getMealByNameUseCase: GetMealByNameUseCase,
    private val getMealByFilterUseCase: GetMealByFilterUseCase
):ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state :StateFlow<SearchState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {

        //切换到页面时默认加载用于筛选的数据
        loadInitialData()
    }

    private fun loadInitialData(){
        getAllAreaUseCase(orderType = OrderType.Ascending)
            .onEach {result ->
                result
                    .onSuccess { area ->
                        _state.update { it.copy(allAreas = area) }
                    }
                    .onFailure { exception ->
                        Log.e("SearchViewModel", "Failed to load areas", exception)
                        _state.update { it.copy(error = exception.message) }
                    }
            }
            .launchIn(viewModelScope)
        getAllCategoryUseCase(orderType = OrderType.Ascending)
            .onEach {result ->
                result
                    .onSuccess { categories ->
                        _state.update { it.copy(allCategories = categories) }
                    }
                    .onFailure { exception ->
                        Log.e("SearchViewModel", "Failed to load categories", exception)
                        _state.update { it.copy(error = exception.message) }
                    }
            }
            .launchIn(viewModelScope)
        getAllIngredientUseCase(orderType = OrderType.Ascending)
            .onEach {result ->
                result
                    .onSuccess { ingredients ->
                        _state.update { it.copy(allIngredients = ingredients) }
                    }
                    .onFailure { exception ->
                        Log.e("SearchViewModel", "Failed to load ingredients", exception)
                        _state.update { it.copy(error = exception.message) }
                    }
            }
            .launchIn(viewModelScope)
    }

    //当ui层搜索框文本变化是，调用此方法
    fun onSearchQueryChanged(query:String){
        _state.update { it.copy(searchQuery = query) }

        //实现搜索防抖
        searchJob?.cancel()//取消上一次搜索
        searchJob = viewModelScope.launch {
            delay(500L)
            executeSearch(query)
        }
    }


    private fun executeSearch(query: String) {

        val searchFlow = getMealByNameUseCase(query)

        if (query.isNotEmpty()) {
            // 1. 在Flow链外部，立即将状态更新为“加载中”
            _state.update { it.copy(isLoading = true, error = null) }
            searchFlow
                .onEach { result ->
                    result
                        .onSuccess { meals ->
                            _state.update { it.copy(isLoading = false, searchResults = meals) }
                        }
                        .onFailure { exception ->
                            _state.update { it.copy(isLoading = false, error = exception.message) }

                        }
                }
                .catch { exceptionF ->
                    _state.update {
                        it.copy(isLoading = false, error = exceptionF.message)
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun onFilterChipClicked(filterType:FilterType){
        _state.update {
            if (it.expandedFilter == filterType){
                it.copy(expandedFilter = null)
            }else{
                it.copy(expandedFilter = filterType)
            }
        }
        Log.d("Filter State","${_state.value.expandedFilter}")
    }
    fun onFilterPopupDismissed(){
        _state.update { it.copy(expandedFilter = null) }
        Log.d("Filter PopUp Dismissed","${_state.value.expandedFilter}")
    }

    fun onAreaSelected(area:String){
        //清空其他筛选并立即搜索
        _state.update {
            it.copy(
                selectedArea = area,
                selectedCategory = null,
                selectedIngredients = emptySet()
            )
        }
        onFilterPopupDismissed()
    }

    //按种类搜索
    fun onCategorySelected(category:String){
        _state.update {
            it.copy(
                selectedCategory = category,
                selectedArea = null,
                selectedIngredients = emptySet()
            )
        }
        onFilterPopupDismissed()
    }
    //已在ui层用一个临时的tempSelectedIngredients代替
//    fun oneIngredientSelect(ingredient:String){
//        //更新临时选中的多选列表，但不立即搜索
//        _state.update { currentState->
//            val currentIngredients = currentState.selectedIngredients
//            val newIngredient = if (ingredient in currentIngredients){
//                currentIngredients - ingredient
//            }else{
//                currentIngredients + ingredient
//            }
//            currentState.copy(
//                selectedIngredients = newIngredient,
//                selectedCategory = null,
//                selectedArea = null)
//        }
//    }

    //重置
    fun resetIngredientFilter(){
        _state.update { it.copy(selectedIngredients = emptySet()) }
        onFilterPopupDismissed()
    }

    fun applyIngredientFilter(confirmedIngredients:Set<String>){
        //用ui传回来的最终数据更新状态
        _state.update { it.copy(selectedIngredients = confirmedIngredients) }
        //点击确定后才开始搜索
        triggerSearch()
        onFilterPopupDismissed()
    }

    private fun triggerSearch(ingredients:MutableSet<String> = mutableSetOf() ) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {

            val currentState = _state.value

            val searchFlow: Flow<Result<List<Meal>>> = when{
                currentState.selectedIngredients.isEmpty() ->{
                    getMealByFilterUseCase(MealFilter.ByIngredient(currentState.selectedIngredients.joinToString(separator = ",")))
                }

                currentState.selectedCategory != null ->{
                    getMealByFilterUseCase(MealFilter.ByCategory(currentState.selectedCategory))
                }

                currentState.selectedArea != null ->{
                    getMealByFilterUseCase(MealFilter.ByArea(currentState.selectedArea))
                }

                else -> {
                    flowOf(Result.success((emptyList())))
            }
        }
            searchFlow
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch{e -> _state.update { it.copy(isLoading = false, error = e.message) }}
                .collect { result ->
                        result
                            .onSuccess { data ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        searchResults = data?: emptyList(),
                                        error = null
                                    )
                                }
                            }
                            .onFailure {exception->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = exception.message
                                    )
                                }
                            }
                    }
        }
    }
}