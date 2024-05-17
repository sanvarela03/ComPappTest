package com.example.compapptest.domain.repository

import com.example.compapptest.data.local.entities.ShoppingCartEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndShoppingCart
import kotlinx.coroutines.flow.Flow


interface ShoppingCartRepository {
    suspend fun addShoppingCartEntity(shoppingCartEntity: ShoppingCartEntity)
    fun getShoppingCartItems(): Flow<List<ShoppingCartEntity>>
    fun getProducerAndShoppingCart(): Flow<List<ProducerAndShoppingCart>>
    fun getProducerAndShoppingCart(producerId: Long): Flow<ProducerAndShoppingCart>
    suspend fun deleteShoppingCartItem(id: Long)
}