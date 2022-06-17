package com.example.geth.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.data.EtherViewModelInterface
import com.example.geth.ui.screen.account.AccountScreen
import com.example.geth.ui.screen.home.HomeScreen
import com.example.geth.ui.screen.settings.SettingsScreen

@Composable
fun MainView(modelInterface: EtherViewModelInterface) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                parentNavHostController = navController,
                modelInterface = modelInterface,
            )
        }
        composable(route = Screen.Account.route) {
            AccountScreen(parentNavController = navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}