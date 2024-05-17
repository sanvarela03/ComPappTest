package com.example.compapptest.domain.use_cases.address

import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.domain.repository.AddressRepository

class GetAddress(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: Long): AddressEntity? {
        return repository.getAddressById(addressId)
    }

}