package com.example.compapptest.domain.use_cases.shoping_cart

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.ShoppingCartEntity
import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.AddressRepository
import com.example.compapptest.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow

class AddShoppingCartItem(
    private val repository: ShoppingCartRepository
) {
    suspend operator fun invoke(shoppingCartEntity: ShoppingCartEntity) {
        repository.addShoppingCartEntity(shoppingCartEntity)
    }
}