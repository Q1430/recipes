package com.recipe.recipes.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavController
import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.presentation.viewmodel.FavoritesViewModel
import com.recipe.recipes.presentation.viewmodel.SearchMealsViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    searchMealsViewModel:SearchMealsViewModel,
    favoritesViewModel: FavoritesViewModel

){
    val searchState by searchMealsViewModel.state.collectAsStateWithLifecycle()
    val favoriteState by favoritesViewModel.state.collectAsState()

    Column (modifier = Modifier.fillMaxSize()) {
        TopSearchBar(
            query = searchState.searchQuery,
            onQueryChange = searchMealsViewModel::onSearchQueryChanged,
            onBackClick = { navController.popBackStack() },
        )
        Divider()
        FilterChipBar(
            state = searchState,
            onFilterClick = searchMealsViewModel::onFilterChipClicked
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (searchState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            searchState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (searchState.searchResults.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text("没搜到，玩去吧")
                }
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(searchState.searchResults){meal ->
                        ResultItem(
                            meal = meal,
                            onItemClick = { navController.navigate("detail_screen/${meal.id}")},
                            onFavoriteClick = {
                                if (favoriteState.favoriteMeals.contains(meal)) {
                                TODO("")
                                }else {
                                    TODO()
                                }
                                              },
                            isFavorite = true
                        )
                    }
                }
            }
        }
    }
    // 5. 根据状态条件渲染弹窗，并将所有事件传递给ViewModel
    FilterGroup(
        state = searchState,
        onDismiss = searchMealsViewModel::onFilterPopupDismissed,
        onAreaSelected = searchMealsViewModel::onAreaSelected,
        onCategorySelected = searchMealsViewModel::onCategorySelected,
        onIngredientReset = searchMealsViewModel::resetIngredientFilter,
        onIngredientsConfirmed = searchMealsViewModel::applyIngredientFilter
    )
}