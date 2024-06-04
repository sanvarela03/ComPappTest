package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResourceEntity(
    @PrimaryKey
    val id: Long?,
    val travelDistance: Double,
    val distanceUnit: String,
    val travelDuration: Double,
    val durationUnit: String,
    val travelMode: String,
    val userId: Long
)