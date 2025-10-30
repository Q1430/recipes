package com.recipe.recipes.presentation.discovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.recipe.recipes.R
import com.recipe.recipes.presentation.navigation.Routes

@Composable
fun DiscoveryFindScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.discovery_back1),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                navController.navigate(Routes.RANDOM_SCREEN)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "今天吃点啥呢")
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DecisionScreenPreview() {
//    DiscoveryFindScreen()
//}
