package com.example.compapptest.domain.use_cases.producer

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress
import com.example.compapptest.data.local.entities.relations.ProducerAndProducts
import com.example.compapptest.domain.repository.ProducerRepository
import kotlinx.coroutines.flow.Flow

class SearchProducer(
    private val repository: ProducerRepository
) {
    suspend operator fun invoke(
        producerId: Long,
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<ProducerAndProducts>> {
        return repository.searchProducer(producerId, fetchFromRemote)
    }
}