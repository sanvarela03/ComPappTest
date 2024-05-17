package com.example.compapptest.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.compapptest.ui.navigation.graphs.Graph
import com.example.compapptest.ui.screens.AddEditAddressScreen
import com.example.compapptest.ui.screens.AddEditCustomerScreen
import com.example.compapptest.ui.screens.AddressListScreen
import com.example.compapptest.ui.screens.OrderListScreen
import com.example.compapptest.ui.screens.ProducerDetailScreen
import com.example.compapptest.ui.screens.ProducerSearchScreen
import com.example.compapptest.ui.screens.SendOrderScreen
import com.example.compapptest.ui.screens.ShoppingCartScreen
import com.example.compapptest.ui.states.HomeState
import com.example.compapptest.ui.viewmodels.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavigation(
    homeViewModel: HomeViewModel = hiltViewModel(),
    homeNavController: NavHostController,
) {
    NavHost(
        route = Graph.HOME,
        navController = homeNavController,
        startDestination = Screen.ProducerSearchScreen.route
    ) {
        composable(Screen.ProducerSearchScreen.route) {
            ProducerSearchScreen(
                navigateTo = {
                    homeNavController.navigate(it) {

                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = Screen.ShoppingCartScreen.route
        ) {
            ShoppingCartScreen(
                navigateTo = {
                    homeNavController.navigate(it)
                }
            )
        }

        composable(
            route = Screen.ProducerDetailScreen.route + "?producerId={producerId}",
            arguments = listOf(
                navArgument(name = "producerId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            ProducerDetailScreen()
        }

        composable(
            route = Screen.SendOrderScreen.route + "?producerId={producerId}",
            arguments = listOf(
                navArgument(name = "producerId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            SendOrderScreen(
                navigateTo = {
                    homeNavController.navigate(it)
                }
            )
        }


        composable(Screen.AddEditCustomerScreen.route) {
            AddEditCustomerScreen()
        }
        composable(Screen.AddressListScreen.route) {
            AddressListScreen(
                addressListViewModel = hiltViewModel(),
                navigateTo = {
                    homeNavController.navigate(it)
                }
            )
        }



        composable(
            route = Screen.AddEditAddressScreen.route + "?addressId={addressId}",
            arguments = listOf(
                navArgument(name = "addressId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            AddEditAddressScreen(
                navigateTo = {
                    homeNavController.navigate(it) {
                        popUpTo(it) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.OrderListScreen.route) {
            OrderListScreen()
        }

        composable(Screen.PendingOrdersScreen.route) {
            Column {
                Text(text = "PendingOrdersScreen")
            }
        }

    }
}