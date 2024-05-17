package com.example.compapptest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse

@Composable
fun AddressItem(
    addressResponse: AddressEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(400.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = addressResponse.name.capitalize(),
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                MyDropdownMenu(
                    onDeleteClick = onDeleteClick,
                    onEditClick = { onEditClick() }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = addressResponse.instruction)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = addressResponse.street, fontStyle = FontStyle.Italic)

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Longitud: ${addressResponse.longitude}   Latitud: ${addressResponse.latitude}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "${addressResponse.city} - ${addressResponse.state} - ${addressResponse.country}",
                fontStyle = FontStyle.Italic
            )

        }
    }
}