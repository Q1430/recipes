
 package com.recipe.recipes.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.recipe.recipes.presentation.discovery.DiscoveryFindScreen
import com.recipe.recipes.presentation.discovery.DiscoveryGetRandomScreen
import com.recipe.recipes.presentation.viewmodel.DiscoveryViewModel

 fun NavGraphBuilder.findGraph(navController: NavController) {

     navigation(
         startDestination = Routes.DISCOVERY_SCREEN,
         route = Routes.FIND_GRAPH
     ){
         composable(Routes.DISCOVERY_SCREEN){ backStackEntry ->
             val findViewModel = navController.getSharedViewModel<DiscoveryViewModel>(
                 route = Routes.FIND_GRAPH,
                 backStackEntry = backStackEntry
             )
             DiscoveryFindScreen(navController)
         }
         composable(Routes.RANDOM_SCREEN){ backStackEntry ->
             val findViewModel = navController.getSharedViewModel<DiscoveryViewModel>(
                 route = Routes.FIND_GRAPH,
                 backStackEntry = backStackEntry
             )
             DiscoveryGetRandomScreen(
                 navController,
                 discoveryViewModel = findViewModel
             )
         }
     }


}