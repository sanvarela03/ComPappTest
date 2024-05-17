package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse

@Entity
data class CustomerEntity(
    @PrimaryKey(autoGenerate = false)
    val customerId: Long,
    val username: String,
    val name: String,
    val lastname: String,
    val email: String,
    val currentAddressId: Long,
) {
    fun toCustomerInfoResponse(
        addressList: List<AddressEntity>,
        orderInfoResponseList: List<OrderInfoResponse>? = emptyList()
    ): CustomerInfoResponse {
        return CustomerInfoResponse(
            customerId = customerId,
            username = username,
            name = name,
            lastname = lastname,
            email = email,
            currentAddressId = currentAddressId,
            addressList = addressList.map { it.toAddressResponse() },
            orderInfoResponseList = if (orderInfoResponseList != null) orderInfoResponseList else emptyList(),
        )
    }
}