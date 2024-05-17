package com.example.compapptest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress

@Composable
fun ProducerItem(
    producerAndAddress: ProducerAndAddress,
    onClick: () -> Unit
) {
    val producer: ProducerEntity = producerAndAddress.producer
    val address: AddressEntity? = producerAndAddress.currentAddress
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
                    modifier = Modifier.weight(4f),
                    text = "${producer.name.replaceFirstChar { it -> it.uppercase() }} ${producer.lastName.split(" ")[0].replaceFirstChar { it -> it.uppercase() }} ${producer.lastName.split(" ")[1].replaceFirstChar { it -> it.uppercase() }} ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalShipping,
                        contentDescription = "Sign out",
                        modifier = Modifier.size(30.dp)
                    )
                }

            }
            Text(text = producer.email)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = producer.phone)
            if (address != null) {
                Row {

                    Spacer(modifier = Modifier.weight(3f))
                    Text(
                        text = address.city.replaceFirstChar { it -> it.uppercase() },
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 7.dp)
                    )
                }
            }


        }
    }
}