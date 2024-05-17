package com.example.compapptest.data.remote.payload.response.producer

data class ProducerDetailResponse(
    val producerSearchResponse: ProducerSearchResponse,
    val productList: List<ProductResponse>
)
