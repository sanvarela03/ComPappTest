package com.example.compapptest.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compapptest.data.local.entities.OrderEntity
import com.example.compapptest.data.local.entities.ProducerInfoEntity
import com.example.compapptest.data.local.entities.TransporterInfoEntity

data class OrderAndTransporterInfo(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val transporterInfoEntity: TransporterInfoEntity
)
