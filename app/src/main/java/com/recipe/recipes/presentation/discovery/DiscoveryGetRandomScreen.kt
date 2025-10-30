package com.recipe.recipes.presentation.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipe.recipes.R
import com.recipe.recipes.presentation.viewmodel.DiscoveryViewModel
import com.recipe.recipes.ui.theme.Blue60
import androidx.compose.runtime.getValue
import com.recipe.recipes.presentation.navigation.Routes


@Composable
fun DiscoveryGetRandomScreen(
    navController: NavController,
    discoveryViewModel: DiscoveryViewModel
){
    val state by discoveryViewModel.state.collectAsState()
    // 1. 使用 Scaffold 作为根布局
    Scaffold(
        // 在这里可以设置 TopBar, BottomBar 等，我们暂时不需要
    ) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Blue60)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.randomMeal?.name ?: state.error.toString(),
                fontSize = 30.sp,
                color = Color.LightGray
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "分类:${state.randomMeal?.category}",
                fontSize = 24.sp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "地区:${state.randomMeal?.area}",
                fontSize = 24.sp,
                color = Color.LightGray
            )
        }


        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            model = state.randomMeal?.thumbnailUrl, // 直接将 String 类型的 URL 赋值给 model
            contentDescription = "从网络加载的图片",
            modifier = Modifier
                .size(400.dp),
            contentScale = ContentScale.Crop,
            // 添加占位图和错误图
            placeholder = painterResource(id = R.drawable.loading_image),
            error = painterResource(id = R.drawable.error_image)
        )
        Column {
            Button(
                onClick = {
                    navController.navigate("detail_screen/${state.randomMeal?.id}")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
            ) {
                Text("好，就决定是你了", modifier = Modifier.padding(vertical = 8.dp), fontSize = 16.sp)
            }
            Button(
                onClick = { navController.navigate(Routes.RANDOM_SCREEN)},
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White)
            ) {
                Text("不想吃这个，下一个", modifier = Modifier.padding(vertical = 8.dp), fontSize = 16.sp)
            }
        }
    }
}
    }
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val sampleMeal = Meal(
//        id = "52814",
//        name = "泰国绿咖喱",
//        category = "鸡肉",
//        area = "泰式",
//        thumbnailUrl = "https://www.themealdb.com/images/media/meals/sstssx1487349585.jpg",
//        // Tweak 4: 使用空值代替 TODO()，让预览更安全
//        instructions = listOf("第一步...", "第二步..."),
//        youtubeUrl = "",
//        tags = listOf("咖喱", "微辣"),
//        ingredients = listOf(Ingredient("土豆", "200g"))
//    )
//    DiscoveryGetRandomScreen(meal = sampleMeal)
//}