package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.response.customer.order.StatusResponse

@Entity
data class StatusEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val statusId: Long?,
    val name: String,
    val createdAt: String,
    val orderId: Long
) {
    fun toStatusResponse(): StatusResponse {
        return StatusResponse(
            statusId = statusId,
            name = name,
            createdAt = createdAt,
            orderId = orderId
        )
    }
}
