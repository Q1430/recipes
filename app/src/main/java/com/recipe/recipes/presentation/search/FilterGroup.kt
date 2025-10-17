package com.recipe.recipes.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.recipe.recipes.presentation.state.FilterType
import com.recipe.recipes.presentation.state.SearchState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterGroup(
    state:SearchState,
    onDismiss:()->Unit,
    onAreaSelected:(String)->Unit,
    onCategorySelected:(String)->Unit,
    onIngredientsConfirmed:(Set<String>)->Unit,
    onIngredientReset:() ->Unit
){
    //只有当筛选器被展开式，才显示Popup
    val filterType = state.expandedFilter?:return

    //临时的多选状态，只在弹窗内部使用
    var tempSelectedIngredients by remember (state.expandedFilter){
        mutableStateOf(state.selectedIngredients)
    }

    Popup (
        alignment = Alignment.TopCenter,
        onDismissRequest = onDismiss
    ){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ){
            Column(Modifier.padding(16.dp)){
                when(filterType){
                    FilterType.AREA ->{
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)){
                            items(state.allAreas){area ->
                                Text(
                                    text = area.strArea,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onAreaSelected(area.strArea)}
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                    FilterType.CATEGORY -> {
                        LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)){
                            items(state.allCategories){category ->
                                Text(
                                    text = category.strCategory,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onCategorySelected(category.strCategory) }
                                )
                            }
                        }
                    }
                    FilterType.INGREDIENT -> {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.allIngredients.forEach{ingredient ->
                                val isSelected = ingredient.strIngredient in tempSelectedIngredients
                                FilterButton(
                                    text = ingredient.strIngredient,
                                    isSelected = isSelected,
                                    onClick = {
                                        tempSelectedIngredients = if (isSelected){
                                            tempSelectedIngredients - ingredient.strIngredient
                                        }else{
                                            tempSelectedIngredients + ingredient.strIngredient
                                        }
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ){
                            Button(
                                onClick = {
                                    tempSelectedIngredients = emptySet()
                                    onIngredientReset()
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0))
                            ) { Text("重置", color = Color.Black)}
                            Button(
                                onClick = { onIngredientsConfirmed(tempSelectedIngredients) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                            ) { Text("确定", color = Color.Black)}
                        }
                    }
                }
            }
        }
    }

}