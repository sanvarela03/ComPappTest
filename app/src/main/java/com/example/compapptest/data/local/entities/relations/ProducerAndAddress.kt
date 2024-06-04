package com.example.compapptest.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.ResourceEntity
import com.example.compapptest.data.remote.payload.response.producer.Resource

data class ProducerAndAddress(
    @Embedded val producer: ProducerEntity,
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
