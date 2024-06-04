package com.example.compapptest.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.ProductEntity
import com.example.compapptest.data.local.entities.ResourceEntity

data class ProducerAndProducts(
    @Embedded val producer: ProducerEntity,
    @Relation(
        parentColumn = "producerId",
        entityColumn = "producerId"
    )
    val productList: List<ProductEntity>,

    @Relation(
        parentColumn = "producerId",
        entityColumn = "userId",

        )
    val currentAddress: AddressEntity?,

    @Relation(
        parentColumn = "producerId",
        entityColumn = "userId"
    )
    val resource: ResourceEntity?
)
