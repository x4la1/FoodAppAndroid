package com.example.testcomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.screens.CustomizeScreen
import com.example.testcomposeapp.screens.HomeScreen
import com.example.testcomposeapp.screens.LoginScreen
import com.example.testcomposeapp.screens.MessageScreen
import com.example.testcomposeapp.screens.PaymentScreen
import com.example.testcomposeapp.screens.ProductScreen
import com.example.testcomposeapp.screens.RegisterScreen
import com.example.testcomposeapp.screens.StartScreen
import com.example.testcomposeapp.screens.UserProfileScreen

sealed class NavRoute(val route: String){
    object StartScreen: NavRoute("start_screen")
    object LoginScreen: NavRoute("login_screen")
    object RegisterScreen: NavRoute("register_screen")
    object HomeScreen: NavRoute("home_screen")
    object ProductScreen: NavRoute("product_screen")
    object CustomizeScreen: NavRoute("customize_screen")
    object PaymentScreen: NavRoute("payment_screen")
    object MessageScreen: NavRoute("message_screen")
    object UserProfileScreen: NavRoute("user_profile_screen")
}


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.LoginScreen.route) {
        composable(NavRoute.StartScreen.route) {StartScreen(navController = navController)}
        composable(NavRoute.LoginScreen.route) {LoginScreen(navController = navController)}
        composable(NavRoute.RegisterScreen.route) {RegisterScreen(navController = navController)}
        composable(NavRoute.HomeScreen.route) {HomeScreen(navController = navController)}
        composable(NavRoute.ProductScreen.route) {ProductScreen(navController = navController)}
        composable(NavRoute.CustomizeScreen.route) {CustomizeScreen(navController = navController)}
        composable(NavRoute.PaymentScreen.route) {PaymentScreen(navController = navController)}
        composable(NavRoute.MessageScreen.route) {MessageScreen(navController = navController)}
        composable(NavRoute.UserProfileScreen.route) {UserProfileScreen(navController = navController)}

    }
}