package com.example.compapptest.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compapptest.data.local.entities.ShoppingCartEntity

@Composable
fun CartItem(
    item: ShoppingCartEntity,
    showDeleteBtn: Boolean = true,
    onDelete: (Long) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(400.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    item.name,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    item.units.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "$ ${item.price.toInt()}",
                    modifier = Modifier.weight(2f)
                )
                Text(
                    "$ ${(item.units * item.price).toInt()}",
                    modifier = Modifier.weight(2f)
                )
                if (showDeleteBtn) {
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onDelete(item.productId) }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "")
                    }
                }
            }
        }
    }
}