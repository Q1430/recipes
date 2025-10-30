package com.recipe.recipes.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.recipe.recipes.presentation.navigation.Routes

@Composable
fun BottomNavigationBar(navController: NavController){
    val items = listOf(
        BottomNavItem(
            label = "发现",
            icon = Icons.Outlined.Home,
            route = Routes.DISCOVERY_SCREEN
        ),
        BottomNavItem(
            label = "搜索",
            icon = Icons.Outlined.Search,
            route = Routes.SEARCH_SCREEN
            // TODO:  改为搜索页
        ),
        BottomNavItem(
            label = "收藏",
            icon = Icons.Outlined.FavoriteBorder, // 收藏图标
            route = Routes.FAVORITE_SCREEN
        )
    )
    NavigationBar {
        // 2. 获取当前导航的后台堆栈入口
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // 3. 从中获取当前路由地址
        val currentRoute = navBackStackEntry?.destination?.route

        // 4. 遍历导航项列表，为每一项创建一个 NavigationBarItem
        items.forEach { item ->
            NavigationBarItem(
                // 5. 判断当前路由是否是此项的路由，来决定是否为“选中”状态
                selected = currentRoute == item.route,
                // 6. 点击事件
                onClick = {
                    navController.navigate(item.route) {
                        // 这个配置非常重要，可以避免在后台堆栈中无限累积页面
                        // 导航到图的起始点，这样按返回键就不会在底部导航的页面间回退
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // 避免在栈顶重复创建同一个页面
                        launchSingleTop = true
                        // 切换回来时，恢复之前的状态
                        restoreState = true
                    }
                },
                // 设置图标和文字
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}