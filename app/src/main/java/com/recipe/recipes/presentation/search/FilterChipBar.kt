package com.recipe.recipes.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.recipe.recipes.presentation.state.FilterType
import com.recipe.recipes.presentation.state.SearchState

@Composable
fun FilterChipBar(
    state:SearchState,
    onFilterClick:(FilterType) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilterChip(
            text = state.selectedArea?:"地区",
            isSelected = state.selectedArea != null,
            onClick = {onFilterClick(FilterType.AREA)}
        )
        FilterChip(
            text = state.selectedCategory ?: "分类",
            isSelected = state.selectedCategory != null,
            onClick = { onFilterClick(FilterType.CATEGORY) }
        )
        FilterChip(
            text = if (state.selectedIngredients.isEmpty())"成分" else "成分- ${state.selectedIngredients.size}",
            isSelected = state.selectedIngredients.isNotEmpty(),
            onClick = {onFilterClick(FilterType.INGREDIENT)}
        )
    }
}