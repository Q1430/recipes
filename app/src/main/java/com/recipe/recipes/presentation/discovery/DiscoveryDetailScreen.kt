package com.recipe.recipes.presentation.discovery

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.recipe.recipes.domain.model.Ingredient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.recipe.recipes.presentation.favorite.FavScreen
import com.recipe.recipes.presentation.viewmodel.MealDetailViewModel
import com.recipe.recipes.util.UiEvent


// --- 主屏幕组件 ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String,
    navController: NavController,
    mealDetailViewModel: MealDetailViewModel = hiltViewModel<MealDetailViewModel>()
) {
    val state by mealDetailViewModel.state.collectAsState()
    val meal = state.meal
    val url: String = meal?.youtubeUrl.toString()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        mealDetailViewModel.eventFlow.collect{ event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        if (!state.isFavorite){
                            // 左侧按钮
                            Button(
                                modifier = Modifier.align(Alignment.CenterStart),
                                onClick = {
                                    navController.navigate(DisScreen.GetRandom.route)
                                }
                            ) {
                                Text(
                                    text = "换一个",
                                    color = Color.Black,
                                    textAlign = TextAlign.Left
                                )
                            }
                        }else{
                            Button(
                                modifier = Modifier.align(Alignment.CenterStart),
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Text(
                                    text = "返回",
                                    color = Color.Black,
                                    textAlign = TextAlign.Left
                                )
                            }
                        }
                        // 中间文字
                        Text(
                            text = "怎么做",
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        // 右侧收藏按钮
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                mealDetailViewModel.onFavoriteToggle()
                            }
                        ) {
                            Icon(
                                imageVector = if (state.isFavorite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Outlined.FavoriteBorder
                                },
                                contentDescription = "收藏",
                                tint = if (state.isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    )
 { paddingValues ->
        // 使用 Column 将可滚动列表和底部按钮分开
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            // 可滚动部分
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // 占据所有可用空间，将按钮推到底部
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    SectionHeader(text = "配料准备")
                }
                item {
                    IngredientsGrid(meal?.ingredients ?: listOf())
                }

                // 2. "开始教学" 标题
                item {
                    SectionHeader(text = "开始教学")
                }
                items(meal?.instructions ?: listOf()) { instruction ->
                    InstructionStep(text = instruction)
                }

                item{
                    VideoTutorialButton(url)
                }
            }
        }
    }
}


// --- 页面子组件 ---

/**
 * 配料准备的表格
 */
@Composable
fun IngredientsGrid(ingredients: List<Ingredient>) {
    val borderColor = Color.LightGray.copy(alpha = 0.7f)
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .border(BorderStroke(1.dp, borderColor))
    ) {
        ingredients.forEach { (name, quantity) ->
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = name, weight = 1.5f)
                TableCell(text = quantity.toString(), weight = 1f)
            }
        }
    }
}

/**
 * 表格中的单元格
 */
@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    val borderColor = Color.LightGray.copy(alpha = 0.7f)
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .border(BorderStroke(0.5.dp, borderColor))
            .padding(12.dp),
        textAlign = TextAlign.Center
    )
}

/**
 * "开始教学" 这样的章节标题
 */
@Composable
fun SectionHeader(text: String) {
    val borderColor = Color.LightGray.copy(alpha = 0.7f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(BorderStroke(1.dp, borderColor))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

/**
 * 单个教学步骤
 */
@Composable
fun InstructionStep(text: String) {
    val borderColor = Color.LightGray.copy(alpha = 0.7f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp) // 每个步骤间的垂直间距
            .border(BorderStroke(1.dp, borderColor))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

/**
 * 底部的圆形视频教学按钮
 */
@Composable
fun VideoTutorialButton(text: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp), // 按钮与列表和底部的距离
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW,text.toUri())
                context.startActivity(intent)
            }
        ) {
            Column(
                modifier = Modifier
                    .clip(CircleShape) // 裁剪成圆形
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Gray), CircleShape)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "文字学不会？", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(Modifier.height(4.dp))
                Text(text = "来看视频教学", fontSize = 14.sp, color = Color.DarkGray)
            }
         }
    }
}
