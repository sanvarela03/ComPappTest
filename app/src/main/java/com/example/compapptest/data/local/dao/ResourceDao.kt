package com.example.compapptest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.compapptest.data.local.entities.ResourceEntity

@Dao
interface ResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resourceEntity: ResourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllResources(resourceEntityList: List<ResourceEntity>)

    @Transaction
    @Query("SELECT * FROM ResourceEntity WHERE  id = :id")
    suspend fun getResource(id: Long): ResourceEntity?

    @Transaction
    @Query("SELECT * FROM ResourceEntity")
    suspend fun getAllAddresses(): List<ResourceEntity>?

    @Transaction
    @Query("SELECT * FROM ResourceEntity WHERE userId = :userId")
    suspend fun getResourceByUserId(userId: Long): ResourceEntity?

    @Transaction
    @Query("DELETE FROM ResourceEntity")
    suspend fun clearResourceEntity()

    @Transaction
    @Query("DELETE FROM ResourceEntity WHERE userId = :userId ")
    suspend fun clearResourceEntityByUserId(userId: Long)
}