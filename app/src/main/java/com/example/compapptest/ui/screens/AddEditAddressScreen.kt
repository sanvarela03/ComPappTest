package com.example.compapptest.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditRoad
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.ui.components.MyDialog
import com.example.compapptest.ui.components.MyNumberFieldComponent
import com.example.compapptest.ui.components.MyPicker3
import com.example.compapptest.ui.events.AddEditAddressEvent
import com.example.compapptest.ui.events.HomeEvent
import com.example.compapptest.ui.navigation.graphs.Graph
import com.example.compapptest.ui.viewmodels.AddEditAddressViewModel
import com.example.compapptest.ui.viewmodels.HomeViewModel
import com.example.protapptest.ui.components.ButtonComponent
import com.example.protapptest.ui.components.HeadingTextComponent
import com.example.compapptest.ui.components.MyTextFieldComponentIcons


@Composable
fun AddEditAddressScreen(
    viewModel: AddEditAddressViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val cityList = state.cityList
    val stateList = state.stateList
    val countryList = state.countryList
    val isCurrentAddressList = listOf("Si", "No")

    val isAttemptingToSave = viewModel.isAttemptingToSave

    dialog(viewModel, isAttemptingToSave, homeViewModel, navigateTo)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            HeadingTextComponent(value = "${if (isAttemptingToSave) "Agregar" else "Editar"} Dirección")
            Spacer(modifier = Modifier.height(40.dp))
            MyTextFieldComponentIcons(
                textValue = state.name,
                labelValue = "Nombre",
                imageVector = Icons.Outlined.Create,
                onTextSelected = {
                    viewModel.onEvent(AddEditAddressEvent.NameChanged(it))
                },
                errorStatus = false
//                signInViewModel.state.usernameError
            )
            MyTextFieldComponentIcons(
                textValue = state.instruction,
                labelValue = "Instrucción",
                imageVector = Icons.Outlined.Description,
                onTextSelected = {
                    viewModel.onEvent(AddEditAddressEvent.InstructionChanged(it))
                },
                errorStatus = false
//                signInViewModel.state.usernameError
            )

            MyTextFieldComponentIcons(
                textValue = state.street,
                labelValue = "Dirección",
                imageVector = Icons.Outlined.EditRoad,
                onTextSelected = {
                    viewModel.onEvent(AddEditAddressEvent.StreetChanged(it))
                },
                errorStatus = false
//                signInViewModel.state.usernameError
            )


            MyPicker3(
                textValue = state.city,
                labelValue = "Ciudad",
                imageVector = Icons.Outlined.LocationCity,
                items = cityList,
                onClick = {
                    viewModel.onEvent(AddEditAddressEvent.CityChanged(cityList[it]))
                },
                errorStatus = false
            )

            MyPicker3(
                textValue = state.state,
                labelValue = "Departamento",
                imageVector = Icons.Outlined.LocationCity,
                items = stateList,
                onClick = {
                    viewModel.onEvent(AddEditAddressEvent.StateChanged(stateList[it]))
                },
                errorStatus = false
            )


            MyPicker3(
                textValue = state.country,
                labelValue = "Pais",
                imageVector = Icons.Outlined.LocationCity,
                items = countryList,
                onClick = {
                    viewModel.onEvent(AddEditAddressEvent.CountryChanged(countryList[it]))
                },
                errorStatus = false
            )


            MyNumberFieldComponent(
                textValue = state.latitude.toString(),
                labelValue = "Latitud",
                imageVector = Icons.Outlined.Straighten,
                onTextSelected = {
//                    signInViewModel.onEvent(SignInUIEvent.UsernameChanged(it))
                    viewModel.onEvent(AddEditAddressEvent.LatitudeChanged(if (it.isNotBlank()) it else "0.0"))
                },
                errorStatus = false,
//                signInViewModel.state.usernameError
            )

            MyNumberFieldComponent(
                textValue = state.longitude.toString(),
                labelValue = "Longitud",
                imageVector = Icons.Outlined.Straighten,
                onTextSelected = {
//                    signInViewModel.onEvent(SignInUIEvent.UsernameChanged(it))
                    viewModel.onEvent(AddEditAddressEvent.LongitudeChanged(if (it.isNotBlank()) it else "0.0"))
                },
                errorStatus = false,
//                signInViewModel.state.usernameError
            )

            Spacer(modifier = Modifier.height(5.dp))


            MyPicker3(
                textValue = if(state.isCurrentAddress) "Si" else "No",
                labelValue = "¿ Establecer como dirección actual?",
                imageVector = Icons.Outlined.MyLocation,
                items = isCurrentAddressList,
                onClick = {
                    viewModel.onEvent(AddEditAddressEvent.IsCurrentAddressChanged(
                        isCurrentAddressList[it] == "Si"
                    ))
                },
                errorStatus = false
            )


            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(
                value = "Guardar Dirección",
                isEnabled = true
            ) {
                Log.d("SignInScreen", "Sign In BTN CLICK!")
                viewModel.onEvent(AddEditAddressEvent.SaveBtnClick)
            }
        }
    }
}

@Composable
private fun dialog(
    viewModel: AddEditAddressViewModel,
    isAttemptingToSave: Boolean,
    homeViewModel: HomeViewModel,
    navigateTo: (String) -> Unit
) {
    val apiResponse by viewModel.apiFlow.collectAsState()
    var msg = ""
    when (apiResponse) {
        is ApiResponse.Failure -> {}
        ApiResponse.Loading -> {}
        is ApiResponse.Success -> {
            msg = (apiResponse as ApiResponse.Success<MessageResponse>).data.message
            viewModel.showDialog = true
        }

        ApiResponse.Waiting -> {

        }
    }

    MyDialog(
        tittle = "Direccion ${if (isAttemptingToSave) "guardada" else "actualizada"} exitosamente",
        text = msg,
        onConfirmTxt = "OK",
        show = viewModel.showDialog,
        onDismiss = { viewModel.showDialog = false },
        onConfirm = {
//            navController.navigate(Screen.HomeDrawerScreen.route)
            viewModel.showDialog = false
            homeViewModel.onEvent(HomeEvent.Refresh)
            navigateTo(Graph.HOME)
        }
    )
}
