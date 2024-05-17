package com.example.compapptest.domain.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress
import com.example.compapptest.data.local.entities.relations.ProducerAndProducts
import kotlinx.coroutines.flow.Flow

interface ProducerRepository {
    suspend fun searchProducers(
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<List<ProducerAndAddress>>>

    suspend fun searchProducer(
        producerId: Long,
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<ProducerAndProducts>>
}