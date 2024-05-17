package com.example.compapptest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.R
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.ui.components.CartItem
import com.example.compapptest.ui.components.MyDialog
import com.example.compapptest.ui.components.MyPicker
import com.example.compapptest.ui.components.MyPicker2
import com.example.compapptest.ui.events.HomeEvent
import com.example.compapptest.ui.events.SendOrderEvent
import com.example.compapptest.ui.events.SignUpEvent
import com.example.compapptest.ui.navigation.graphs.Graph
import com.example.compapptest.ui.viewmodels.SendOrderViewModel
import com.example.protapptest.ui.components.ButtonComponent

@Composable
fun SendOrderScreen(
    viewModel: SendOrderViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {
    val apiResponse by viewModel.apiFlow.collectAsState()

    var msg = ""
    when (apiResponse) {
        is ApiResponse.Failure -> {}
        ApiResponse.Loading -> {}
        is ApiResponse.Success -> {
            msg = (apiResponse as ApiResponse.Success<MessageResponse>).data.message
            viewModel.showDialog = true
        }

        ApiResponse.Waiting -> {

        }
    }

    MyDialog(
        tittle = "Pedido agregado correctamente",
        text = msg,
        onConfirmTxt = "OK",
        show = viewModel.showDialog,
        onDismiss = { viewModel.showDialog = false },
        onConfirm = {
            viewModel.showDialog = false
            navigateTo(Graph.HOME)
        }
    )


    val producerAndShoppingCart =
        viewModel.producerAndShoppingCart.collectAsState(initial = null).value

    val state = viewModel.state

    state.producerAndShoppingCart = producerAndShoppingCart

    val addressList = viewModel.state.addressList

    val addressNames = addressList.map { it.name }

    if (producerAndShoppingCart != null) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Detalle del pedido",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = producerAndShoppingCart.producer.name)
            Spacer(modifier = Modifier.height(15.dp))

            MyPicker2(
                labelValue = "Metodo de Pago",
                imageVector = Icons.Filled.Payments,
                items = listOf("Pago En Efectivo"),
                onClick = {
                    state.paymentMethodId = "p0"
                },
                errorStatus = true
            )

            MyPicker2(
                labelValue = "Dirección de engrega",
                imageVector = Icons.Filled.PinDrop,
                items = addressNames,
                onClick = {
                    val addressId = addressList.get(it).id
                    state.addressId = addressId
                },
                errorStatus = true
            )

            Spacer(modifier = Modifier.height(15.dp))


            LazyColumn {
                items(producerAndShoppingCart.shoppingItems) {
                    CartItem(item = it, showDeleteBtn = false) {

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            ButtonComponent(
                value = "Enviar Pedido",
                isEnabled = true
            ) {
                viewModel.onEvent(SendOrderEvent.SendOrder)
            }
        }

    }
}