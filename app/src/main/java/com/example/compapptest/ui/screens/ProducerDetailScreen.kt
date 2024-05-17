package com.example.compapptest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.ui.components.ProducerItem
import com.example.compapptest.ui.events.ProducerDetailEvent
import com.example.compapptest.ui.events.ProducerSearchEvent
import com.example.compapptest.ui.viewmodels.ProducerDetailViewModel
import com.example.protapptest.ui.components.ProductItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProducerDetailScreen(
    viewModel: ProducerDetailViewModel = hiltViewModel()
) {
    val productList = viewModel.state.productsList
    val producer = viewModel.state.producer
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )


    Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            if (producer != null) {
                Text(
                    text = "${producer.name.replaceFirstChar { it.uppercase() }} ${producer.lastName.replaceFirstChar { it.uppercase() }}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = producer.email)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = producer.phone)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Productos Disponibles",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(ProducerDetailEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (productList.isNotEmpty()) {
                    items(productList) { productEntity ->
                        ProductItem(
                            productEntity,
                            onAddToChoppingCart = {
                                viewModel.onEvent(
                                    ProducerDetailEvent.AddProductToCart(
                                        productEntity.toShoppingCartEntity(
                                            it
                                        )
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

    }
}