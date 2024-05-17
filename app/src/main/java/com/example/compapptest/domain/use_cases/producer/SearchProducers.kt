package com.example.compapptest.domain.use_cases.producer

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress
import com.example.compapptest.domain.repository.AddressRepository
import com.example.compapptest.domain.repository.ProducerRepository
import kotlinx.coroutines.flow.Flow

class SearchProducers(
    private val repository: ProducerRepository
) {
    suspend operator fun invoke(fetchFromRemote: Boolean): Flow<ApiResponse<List<ProducerAndAddress>>> {
        return repository.searchProducers(fetchFromRemote)
    }
}