package com.example.geth.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.ui.AccountScreen
import com.example.geth.ui.InfoScreen
import com.example.geth.ui.screen.home.HomeScreen
import com.example.geth.ui.screen.settings.SettingsScreen



@Composable
fun MainView() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(parentNavHostController = navController)
        }
        composable(route = Screen.Account.route) {
            AccountScreen(navController = navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

    }
}

@Composable
fun TopBar(
    navController: NavController,
    title: String,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Account.route)
            }) {
                Icon(imageVector = Screen.Account.icon, contentDescription = Screen.Account.description)
            }
            IconButton(onClick = {
                navController.navigate(Screen.Settings.route)
            }) {
                Icon(imageVector = Screen.Settings.icon, contentDescription = Screen.Settings.description)
            }
        },
    )
}