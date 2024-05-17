package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.response.customer.contact.TransporterContactInfoResponse

@Entity
data class TransporterInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val transporterId: Long?,
    val completeName: String?,
    val phone: String?,
    val email: String?,
    val orderId: Long?
) {
    fun toTransporterContactInfoResponse(): TransporterContactInfoResponse {
        return TransporterContactInfoResponse(
            transporterId = transporterId,
            completeName = completeName,
            phone = phone,
            email = email,
        )
    }
}
