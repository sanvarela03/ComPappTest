package com.example.compapptest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.compapptest.data.local.entities.ShoppingCartEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndShoppingCart
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingCartEntity(shoppingCartEntity: ShoppingCartEntity)

    @Transaction
    @Query("SELECT * FROM ShoppingCartEntity WHERE producerId = :producerId")
    suspend fun getShoppingCartEntity(producerId: Long): List<ShoppingCartEntity>?

    @Transaction
    @Query("SELECT * FROM ShoppingCartEntity")
    fun getShoppingCartEntity(): Flow<List<ShoppingCartEntity>>

    @Transaction
    @Query("DELETE FROM ShoppingCartEntity WHERE productId = :id ")
    suspend fun deleteShoppingCartEntity(id: Long)

    @Transaction
    @Query("DELETE FROM ShoppingCartEntity WHERE producerId = :producerId ")
    suspend fun clearShoppingCartEntity(producerId: Long)

    @Transaction
    @Query("SELECT * FROM ProducerEntity")
    fun getProducerAndShoppingCart(): Flow<List<ProducerAndShoppingCart>>

    @Transaction
    @Query("SELECT * FROM ProducerEntity WHERE producerId = :producerId")
    fun getProducerAndShoppingCart(producerId: Long): Flow<ProducerAndShoppingCart>
}