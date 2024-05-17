package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.response.customer.contact.CustomerContactInfoResponse

@Entity
data class CustomerInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val customerId: Long,
    val completeName: String,
    val phone: String,
    val email: String,
    val orderId: Long
) {
    fun toCustomerContactInfoResponse(): CustomerContactInfoResponse {
        return CustomerContactInfoResponse(
            customerId = customerId,
            completeName = completeName,
            phone = phone,
            email = email,
        )
    }
}
