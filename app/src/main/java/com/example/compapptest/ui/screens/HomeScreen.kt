package com.example.compapptest.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compapptest.R
import com.example.compapptest.ui.components.HomeBottomBar
import com.example.compapptest.ui.components.NavigationDrawerHeader
import com.example.compapptest.ui.events.AddressListEvent
import com.example.compapptest.ui.events.HomeEvent
import com.example.compapptest.ui.navigation.HomeNavigation
import com.example.compapptest.ui.navigation.Screen
import com.example.compapptest.ui.viewmodels.HomeViewModel
import com.example.protapptest.ui.components.AppToolBar
import com.example.protapptest.ui.components.NavigationDrawerBody
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    HomeDrawer(
        homeViewModel = hiltViewModel(),
        homeNavController = rememberNavController()
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeDrawer(
    homeViewModel: HomeViewModel,
    homeNavController: NavHostController
) {
    val screens by
    remember {
        mutableStateOf(
            listOf(
                Screen.AddressListScreen.route,
                Screen.OrderListScreen.route,
                Screen.ProducerSearchScreen.route,
                Screen.ProducerDetailScreen.route
            )
        )
    }

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showTopBar = screens.any { it == currentDestination?.route }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val state = homeViewModel.state

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = homeViewModel.state.isRefreshing
    )

    val lifecycleOwner = LocalLifecycleOwner.current.lifecycle.currentState.name

    Log.d("HomeScreen", "lifecycleOwner : $lifecycleOwner")


    val local = LocalLifecycleOwner.current


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (showTopBar) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        homeViewModel.onEvent(HomeEvent.Refresh)
                    }
                ) {

                    ModalDrawerSheet {
                        NavigationDrawerHeader(value = state.customerInfoResponse?.email)
                        NavigationDrawerBody(homeViewModel.navigationItemsList) {
                            scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                            Log.d("HomeScreen", "${it.itemId}  ${it.title}  clickeado!")

                            when (it.itemId) {
                                "addressScreen" -> {
                                    homeNavController.navigate(Screen.AddressListScreen.route) {
                                        popUpTo(Screen.AddressListScreen.route) {
                                            inclusive = true
                                        }
                                    }

                                }

                                "homeScreen" -> {
                                    homeNavController.navigate(Screen.ProducerSearchScreen.route) {
                                        popUpTo(Screen.ProducerSearchScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }

                                "ordersScreen" -> {
                                    homeNavController.navigate(Screen.OrderListScreen.route) {
                                        popUpTo(Screen.OrderListScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    AppToolBar(
                        toolbarTitle = stringResource(id = R.string.home),
                        signOutButtonClicked = {
                            Log.d("HomeScreen", "signOutButtonClicked !!!")
                            homeViewModel.onEvent(
                                HomeEvent.SignOutBtnClicked(
                                    local.lifecycle.coroutineScope.coroutineContext
                                )
                            )
                        },
                        navButtonClicked = {
                            scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                        }
                    )
                }
            },
            bottomBar = {
                HomeBottomBar(
                    navigationItems = homeViewModel.bottomNavigationItemsList,
                    currentDestination = homeNavController.currentBackStackEntryAsState().value?.destination,
                    navigateTo = {
                        homeNavController.navigate(it)
//                        {
//                            popUpTo(homeNavController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            restoreState = true
//                            launchSingleTop = true
//                        }
                    }
                )
            }
        ) { contentPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                HomeNavigation(homeNavController = homeNavController)
            }
        }
    }
}