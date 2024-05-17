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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.ui.components.ProducerItem
import com.example.compapptest.ui.events.AddressListEvent
import com.example.compapptest.ui.events.ProducerSearchEvent
import com.example.compapptest.ui.navigation.Screen
import com.example.compapptest.ui.viewmodels.ProducerSearchViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProducerSearchScreen(
    viewModel: ProducerSearchViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {
    val producerList = viewModel.state.producerList
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Buscar productores",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(15.dp))

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(ProducerSearchEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (producerList.isNotEmpty()) {
                    items(producerList) {
                        ProducerItem(
                            it,
                            onClick = {
                                navigateTo(Screen.ProducerDetailScreen.route + "?producerId=${it.producer.producerId}")
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }

    }
}