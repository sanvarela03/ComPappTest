package com.example.compapptest.domain.use_cases.shoping_cart

import com.example.compapptest.data.local.entities.ShoppingCartEntity
import com.example.compapptest.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingCartItems(
    private val repository: ShoppingCartRepository
) {
    operator fun invoke(): Flow<List<ShoppingCartEntity>> {
        return repository.getShoppingCartItems()
    }
}
