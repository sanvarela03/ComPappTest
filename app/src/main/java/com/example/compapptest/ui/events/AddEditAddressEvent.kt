package com.example.compapptest.ui.events

sealed class AddEditAddressEvent {
    data class NameChanged(val name: String) : AddEditAddressEvent()
    data class CountryChanged(val country: String) : AddEditAddressEvent()
    data class StateChanged(val state: String) : AddEditAddressEvent()
    data class CityChanged(val city: String) : AddEditAddressEvent()
    data class StreetChanged(val street: String) : AddEditAddressEvent()
    data class InstructionChanged(val instruction: String) : AddEditAddressEvent()
    data class IsCurrentAddressChanged(val isCurrentAddress: Boolean) : AddEditAddressEvent()
    data class LatitudeChanged(val latitude: String) : AddEditAddressEvent()
    data class LongitudeChanged(val longitude: String) : AddEditAddressEvent()
    object SaveBtnClick : AddEditAddressEvent()
}