package com.example.testcomposeapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testcomposeapp.screens.HomeScreen
import com.example.testcomposeapp.screens.LoginScreen
import com.example.testcomposeapp.screens.PaymentScreen
import com.example.testcomposeapp.screens.ProductScreen
import com.example.testcomposeapp.screens.StartScreen
import com.example.testcomposeapp.screens.UserProfileScreen
import com.example.testcomposeapp.viewmodel.OrderViewModel

sealed class NavRoute(val route: String) {
    object StartScreen : NavRoute("start_screen")
    object LoginScreen : NavRoute("login_screen")
    object HomeScreen : NavRoute("home_screen")
    object ProductScreen : NavRoute("product_screen/{productUid}")
    object PaymentScreen : NavRoute("payment_screen")
    object UserProfileScreen : NavRoute("user_profile_screen")
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost() {
    val orderViewModel = OrderViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.StartScreen.route) {
        composable(NavRoute.StartScreen.route) { StartScreen(navController = navController) }
        composable(NavRoute.LoginScreen.route) { LoginScreen(navController = navController) }
        composable(NavRoute.HomeScreen.route) { HomeScreen(navController = navController) }
        composable(
            NavRoute.ProductScreen.route,
            arguments = listOf(navArgument("productUid") { type = NavType.StringType })
        ) { backStackEntry ->
            val productUid = backStackEntry.arguments?.getString("productUid")
            ProductScreen(navController = navController, orderViewModel, productUid)
        }
        composable(NavRoute.PaymentScreen.route) {
            PaymentScreen(navController = navController, orderViewModel)
        }
        composable(NavRoute.UserProfileScreen.route) { UserProfileScreen(navController = navController) }

    }
}