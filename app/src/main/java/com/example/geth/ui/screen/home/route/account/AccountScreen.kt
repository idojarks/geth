package com.example.geth.ui.screen.home.route.account

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.ui.screen.AccountSubScreen
import com.example.geth.ui.screen.home.route.account.route.AccountDetailScreen
import com.example.geth.ui.screen.home.route.account.route.AllAccountsScreen

@Composable
fun AccountScreen() {
    val navController = rememberNavController()
    val model = Dragon721ViewModelProvider.current

    NavHost(
        navController = navController,
        startDestination = AccountSubScreen.All.route,
    ) {
        // all
        composable(route = AccountSubScreen.All.route) {
            AllAccountsScreen(
                navController = navController,
            )
        }

        // new or edit
        composable(
            route = "account?address={address}",
            arguments = listOf(
                navArgument("address") {
                    nullable = true
                },
            ),
        ) {
            val account = it.arguments?.getString("address")
                ?.let { address ->
                    model.getAccount(address)
                }

            AccountDetailScreen(
                navController = navController,
                account = account,
            )
        }
    }
}