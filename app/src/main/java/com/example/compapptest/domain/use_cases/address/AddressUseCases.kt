package com.example.compapptest.domain.use_cases.address

data class AddressUseCases(
    val getAddress: GetAddress,
    val addAddress: AddAddress,
    val deleteAddress: DeleteAddress,
    val updateAddress: UpdateAddress,
    val getAllAddresses: GetAllAddresses
)
