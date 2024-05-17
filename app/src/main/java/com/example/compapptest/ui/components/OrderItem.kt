package com.example.compapptest.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compapptest.data.local.entities.OrderEntity
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderItem(orderAndStatus: OrderAndStatus) {
    val order: OrderEntity = orderAndStatus.order
    val statusList = orderAndStatus.statusList
    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(400.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 15.dp)
        ) {
            Text(text = "Order Id: ${order.orderId}")
//            Text(text = "Productos: ${order.}")
            Row {

                Text(text = "Costo: ")
                Text(
                    text = " $ ${order.orderCost}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Text(
                text = "Volumen:  ${
                    limitDecimalsUsingStringFormat(
                        order.orderVolume * 1000,
                        1
                    )
                } L"
            )
            Text(text = "Peso:  ${order.orderWeight} Kg")
//            Text(text = "Productor: ${order.producerContactInfoResponse.completeName}")
//            Text(text = "Transportador: ${orderInfoResponse.transporterContactInfoResponse?.completeName}")


            Row {
                Text(text = "Fecha de entrega: ")
                Text(
                    text = "${dateFormatter(order.maxDeliveryDate).dayOfWeek} ${
                        dateFormatter(
                            order.maxDeliveryDate
                        ).dayOfMonth
                    } ${dateFormatter(order.maxDeliveryDate).month}".lowercase(),
                    fontStyle = FontStyle.Italic

                )
            }


            val status = statusList.map {
                val text = it.createdAt
                val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                val localDateTime = dateFormatter(text)
                val formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a") // Formateador para obtener la hora en formato am/pm
                val formattedDateTime = localDateTime.format(formatter)
                val fecha = formattedDateTime.split(" ")[0]
                val hora = formattedDateTime.split(" ")[1]  // Obtiene la parte de la hora
                val amPm = formattedDateTime.split(" ")[2]
                "${it.name}\n${fecha} \n $hora $amPm "

            }
            Stepper(
                numberOfSteps = statusList.size + 1,
                currentStep = statusList.size,
                stepDescriptionList = status,
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(text: String): LocalDateTime {
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val localDateTime = LocalDateTime.parse(text, pattern)
    return localDateTime
}

fun limitDecimalsUsingStringFormat(number: Double, decimals: Int): String {
    return String.format("%.${decimals}f", number)
}