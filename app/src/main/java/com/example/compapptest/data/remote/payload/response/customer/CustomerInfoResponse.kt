package com.example.compapptest.data.remote.payload.response.customer

import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.CustomerEntity
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse

data class CustomerInfoResponse(
    val customerId: Long,
    val username: String,
    val name: String,
    val lastname: String,
    val email: String,
    val currentAddressId: Long,
    val addressList: List<AddressResponse>,
    val orderInfoResponseList: List<OrderInfoResponse>,
) {
    fun toCustomerEntity(): CustomerEntity {
        return CustomerEntity(
            customerId = customerId,
            username = username,
            name = name,
            lastname = lastname,
            email = email,
            currentAddressId = currentAddressId,
        )
    }
}
