package com.example.compapptest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.ui.components.CartItem
import com.example.compapptest.ui.components.ProducerAndOrderItem
import com.example.compapptest.ui.events.ShoppingCartEvent
import com.example.compapptest.ui.viewmodels.ShoppingCartViewModel


@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {
    val items = viewModel.items.collectAsState(initial = emptyList()).value
    val producerAndItems = viewModel.producerAndItems.collectAsState(initial = emptyList()).value


    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Pedidos en progreso",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (producerAndItems.isNotEmpty()) {
                items(producerAndItems) { producerAndItems ->
                    if (producerAndItems.shoppingItems.isNotEmpty()) {

                        ProducerAndOrderItem(
                            producerAndItems,
                            navigateTo = {
                                navigateTo(it)
                            },
                            onDelete = {
                                viewModel.onEvent(ShoppingCartEvent.DeleteItem(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
//            if (items.isNotEmpty()) {
//                items(items) {
//                    CartItem(
//                        item = it,
//                        onDelete = {
//                            viewModel.onEvent(ShoppingCartEvent.DeleteItem(it!!))
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//            }
        }
    }
}