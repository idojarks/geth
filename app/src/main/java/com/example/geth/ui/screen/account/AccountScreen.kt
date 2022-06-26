package com.example.geth.ui.screen.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.ui.screen.AccountSubScreen
import com.example.geth.ui.screen.account.sub.AccountListSubScreen
import com.example.geth.ui.screen.account.sub.NewAccountSubScreen

@Composable
fun AccountScreen(mainNavController: NavController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AccountSubScreen.AccountList.route,
    ) {
        // account list
        composable(route = AccountSubScreen.AccountList.route) {
            AccountListSubScreen(
                mainNavController = mainNavController,
                navController = navController,
            )
        }
        // new account
        composable(route = AccountSubScreen.NewAccount.route) {
            NewAccountSubScreen(
                navController = navController,
            )
        }
    }
}


@Preview
@Composable
fun PreviewAccountView() {
    val navController = rememberNavController()
    AccountScreen(navController)
}