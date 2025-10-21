package com.recipe.recipes.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recipe.recipes.presentation.viewmodel.SearchMealsViewModel

@Composable
fun SearchScreen(
    viewModel:SearchMealsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column (modifier = Modifier.fillMaxSize()) {
        TopSearchBar(
            query = state.searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            onBackClick = { TODO("处理返回事件") },
        )
        Divider()
        FilterChipBar(
            state = state,
            onFilterClick = viewModel::onFilterChipClicked
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                //先用这个测试
                items(state.searchResults) { meal ->
                    // 在这里放置您的菜谱列表项UI
                    Text(text = meal.name, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
    // 5. 根据状态条件渲染弹窗，并将所有事件传递给ViewModel
    FilterGroup(
        state = state,
        onDismiss = viewModel::onFilterPopupDismissed,
        onAreaSelected = viewModel::onAreaSelected,
        onCategorySelected = viewModel::onCategorySelected,
        onIngredientReset = viewModel::resetIngredientFilter,
        onIngredientsConfirmed = viewModel::applyIngredientFilter
    )
}