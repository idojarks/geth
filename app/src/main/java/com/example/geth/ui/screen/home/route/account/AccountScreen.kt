package com.example.geth.ui.screen.home.route.account.sub

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.ui.screen.AccountSubScreen
import com.example.geth.ui.screen.home.route.account.route.AllAccountsScreen

@Composable
fun AccountScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AccountSubScreen.All.route,
    ) {
        // all
        composable(route = AccountSubScreen.All.route) {
            AllAccountsScreen(navController = navController)
        }

        // new
        composable(route = AccountSubScreen.New.route) {
            NewAccountScreen(navController = navController)
        }
    }
}