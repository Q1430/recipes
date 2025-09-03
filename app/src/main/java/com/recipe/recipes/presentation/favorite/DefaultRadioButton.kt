package com.recipe.recipes.presentation.favorite

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.recipe.recipes.util.OrderType


@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect:() -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    orderType: OrderType,
    onOrderChange:(OrderType) -> Unit
){
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        DefaultRadioButton(
            text = "Ascending",
            selected = orderType is OrderType.Ascending,
            onSelect = {onOrderChange(OrderType.Ascending)}
        )
        Spacer(modifier = Modifier.width(10.dp))
        DefaultRadioButton(
            text = "Descending",
            selected = orderType is OrderType.Descending,
            onSelect = {onOrderChange(OrderType.Descending)}
        )
    }

}