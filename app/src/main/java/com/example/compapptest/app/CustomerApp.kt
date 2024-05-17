package com.example.compapptest.app

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compapptest.config.common.UserAuthState
import com.example.compapptest.ui.events.SplashEvent
import com.example.compapptest.ui.navigation.Screen
import com.example.compapptest.ui.navigation.graphs.Graph
import com.example.compapptest.ui.navigation.graphs.authNavGraph
import com.example.compapptest.ui.screens.HomeScreen
import com.example.compapptest.ui.screens.SignInScreen
import com.example.compapptest.ui.screens.SignUpScreen
import com.example.compapptest.ui.screens.SplashScreen
import com.example.compapptest.ui.screens.TermsAndConditionsScreen
import com.example.compapptest.ui.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomerApp(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        authViewModel.onEvent(SplashEvent.CheckAuthentication)
    }
    val navController = rememberNavController()
    val userState = authViewModel.isAuthenticated.collectAsState().value

    NavHost(
        navController = navController,
        startDestination =
        when (userState) {
            UserAuthState.UNKNOWN -> {
                Screen.SplashScreen.route
            }

            UserAuthState.UNAUTHENTICATED -> {
                Graph.AUTHENTICATION
            }

            UserAuthState.AUTHENTICATED -> {
                Screen.HomeScreen.route
            }
        }

    ) {
        authNavGraph(navController)
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}