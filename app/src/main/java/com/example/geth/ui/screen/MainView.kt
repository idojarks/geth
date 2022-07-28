package com.example.geth.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.ui.screen.home.HomeScreen
import com.example.geth.ui.screen.home.route.account.sub.AccountScreen

@Composable
fun MainView() {
    val model = LocalEtherViewModelProvider.current
    val openAccountScreen = model.openAccountScreen.observeAsState(false)

    if (openAccountScreen.value) {
        AccountScreen()
    } else {
        HomeScreen()
    }

/*
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                mainNavController = navController,
            )
        }
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }
    }

 */
}

@Preview
@Composable
private fun Preview() {
    MainView()
}