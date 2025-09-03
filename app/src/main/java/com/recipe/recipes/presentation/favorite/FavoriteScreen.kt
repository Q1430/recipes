package com.recipe.recipes.presentation.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.recipe.recipes.presentation.discovery.DisScreen
import com.recipe.recipes.presentation.favorite.FavoriteItem
import com.recipe.recipes.presentation.viewmodel.FavoritesViewModel
import com.recipe.recipes.util.OrderType

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel<FavoritesViewModel>()
) {
    // 订阅 ViewModel 的 state，当 state 变化时，UI 会自动重组
    val state by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 排序按钮区域
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.onSortOrderChange(OrderType.Descending) },
                    // 如果当前已经是降序，则按钮不可用
                    enabled = state.orderType !is OrderType.Descending
                ) {
                    Text("按最新收藏")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { viewModel.onSortOrderChange(OrderType.Ascending) },
                    // 如果当前已经是升序，则按钮不可用
                    enabled = state.orderType !is OrderType.Ascending
                ) {
                    Text("按最早收藏")
                }
            }

            // 收藏列表区域
            if (state.favoriteMeals.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "你的收藏夹是空的哦！")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(state.favoriteMeals, key = { it.id }) { meal ->
                        FavoriteItem(
                            meal = meal,
                            onItemClick = { mealId ->
                                navController.navigate(DisScreen.MealDetail.createRoute(mealId))
                            },
                            onFavoriteClick = { mealId ->
                                viewModel.onFavoriteClicked(mealId)
                            }
                        )
                    }
                }
            }
        }
    }
}
