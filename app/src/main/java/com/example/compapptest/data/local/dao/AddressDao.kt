package com.example.compapptest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.compapptest.data.local.entities.AddressEntity

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressEntity: AddressEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAddress(addressEntity: List<AddressEntity>)

    @Transaction
    @Query("SELECT CustomerEntity.currentAddressId FROM CustomerEntity WHERE customerId = :customerId")
    suspend fun getCurrentAddressId(customerId: Long): Long?

    @Transaction
    @Query("SELECT * FROM AddressEntity WHERE  id = :addressId")
    suspend fun getAddress(addressId: Long): AddressEntity?

    @Transaction
    @Query("SELECT * FROM AddressEntity")
    suspend fun getAllAddresses(): List<AddressEntity>?

    @Transaction
    @Query("SELECT * FROM AddressEntity WHERE userId = :userId")
    suspend fun getAllAddressesByUserId(userId: Long): List<AddressEntity>?

    @Transaction
    @Query("DELETE FROM AddressEntity")
    suspend fun clearAddressEntity()

    @Transaction
    @Query("DELETE FROM AddressEntity WHERE userId = :userId ")
    suspend fun clearAddressEntityByUserId(userId: Long)
}