package com.example.compapptest.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import com.example.compapptest.ui.components.AddressItem
import com.example.compapptest.ui.events.AddressListEvent
import com.example.compapptest.ui.navigation.Screen
import com.example.compapptest.ui.viewmodels.AddressListViewModel
import com.example.compapptest.ui.viewmodels.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun AddressListScreen(
    addressListViewModel: AddressListViewModel,
    navigateTo: (String) -> Unit
) {
    val addressList = addressListViewModel.state.addressList

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = addressListViewModel.state.isRefreshing
    )

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mis direcciones",
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
                addressListViewModel.onEvent(AddressListEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (addressList.isNotEmpty()) {
                    items(addressList) { address ->
                        AddressItem(
                            addressResponse = address,
                            onEditClick = {
                                navigateTo(
                                    Screen.AddEditAddressScreen.route + "?addressId=${address.id}"
                                )
                            },
                            onDeleteClick = {}
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
    MyFloatingButton(navigateTo)
}

@Composable
private fun MyFloatingButton(navigateTo: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Agregar dirección") },
            icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.LightGray,
            onClick = { navigateTo(Screen.AddEditAddressScreen.route) },
        )
    }
}

