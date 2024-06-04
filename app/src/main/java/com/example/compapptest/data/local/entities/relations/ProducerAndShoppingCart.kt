package com.example.compapptest.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.ResourceEntity
import com.example.compapptest.data.local.entities.ShoppingCartEntity

data class ProducerAndShoppingCart(
    @Embedded val producer: ProducerEntity,
    @Relation(
        parentColumn = "producerId",
        entityColumn = "producerId"
    )
    val shoppingItems: List<ShoppingCartEntity>,

    @Relation(
        parentColumn = "producerId",
        entityColumn = "userId"
    )
    val resourceEntity: ResourceEntity
)
