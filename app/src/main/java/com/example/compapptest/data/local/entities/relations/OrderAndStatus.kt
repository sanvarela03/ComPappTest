package com.example.compapptest.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compapptest.data.local.entities.OrderEntity
import com.example.compapptest.data.local.entities.StatusEntity
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import com.example.compapptest.data.remote.payload.response.customer.contact.CustomerContactInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.contact.ProducerContactInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.contact.TransporterContactInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.order.StatusResponse
import com.example.compapptest.data.remote.payload.response.customer.order.product.ProductResponse

data class OrderAndStatus(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val statusList: List<StatusEntity>
) {
    fun toOrderInfoResponse(
        customerContactInfoResponse: CustomerContactInfoResponse,
        producerContactInfoResponse: ProducerContactInfoResponse,
        transporterContactInfoResponse: TransporterContactInfoResponse?,
        pickupAddress: AddressResponse,
        deliveryAddress: AddressResponse,
        items: List<ProductResponse> = emptyList()
    ): OrderInfoResponse {
        return OrderInfoResponse(
            orderId = order.orderId,
            orderCost = order.orderCost,
            orderWeight = order.orderWeight,
            orderVolume = order.orderVolume,
            estimatedTravelDistance = order.estimatedTravelDistance,
            estimatedTravelDuration = order.estimatedTravelDuration,
            shippingCost = order.shippingCost,
            paymentMethod = order.paymentMethod,
            maxDeliveryDate = order.maxDeliveryDate,
            estimatedPickupDate = if (order.estimatedPickupDate != null) order.estimatedPickupDate else "",
            customerContactInfoResponse = customerContactInfoResponse,
            producerContactInfoResponse = producerContactInfoResponse,
            transporterContactInfoResponse = transporterContactInfoResponse,
            pickupAddress = pickupAddress,
            deliveryAddress = deliveryAddress,
            statusList = statusList.map { it.toStatusResponse() },
            items = items,

            )
    }
}
