package com.example.compapptest.ui.states

import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.request.UpdateAddressRequest

data class AddEditAddressState(
    val name: String = "",
    val country: String = "",
    val state: String = "",
    val city: String = "",
    val street: String = "",
    val instruction: String = "",
    val isCurrentAddress: Boolean = false,
    val latitude: String = "",
    val longitude: String = "",
    val cityList: List<String> = listOf("Ubaque", "Choachi", "Cáqueza", "Chipaque", "Bogotá"),
    val stateList: List<String> = listOf("Cundinamarca", "Meta"),
    val countryList: List<String> = listOf("Colombia"),
) {
    fun toAddressRequest(): AddressRequest {
        return AddressRequest(
            name = name,
            country = country,
            state = state,
            city = city,
            street = street,
            instruction = instruction,
            isCurrentAddress = isCurrentAddress,
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )
    }

    fun toUpdateAddressRequest(id: Long): UpdateAddressRequest {
        return UpdateAddressRequest(
            id = id,
            name = name,
            street = street,
            instruction = instruction,
            isCurrentAddress = isCurrentAddress,
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )
    }
}