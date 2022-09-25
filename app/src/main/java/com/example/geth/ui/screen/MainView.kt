package com.example.geth.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.Contracts_Dragon721_sol_Dragon721.Artwork
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.data.EtherViewModel
import com.example.geth.ui.screen.home.HomeScreen
import com.example.geth.ui.screen.home.route.account.AccountScreen
import com.example.geth.ui.screen.home.route.contract.ContractsScreen
import com.example.geth.ui.screen.home.route.info.Web3Info

val RootNavController = compositionLocalOf<NavHostController> {
    error("")
}

@Composable
fun MainView(
    viewModel: EtherViewModel<Contracts_Dragon721_sol_Dragon721, Artwork>,
) {
    val navController = rememberNavController()

    CompositionLocalProvider(
        Dragon721ViewModelProvider provides viewModel,
        RootNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("accounts") {
                AccountScreen()
            }
            composable("contracts") {
                ContractsScreen()
            }
            composable("dragon721Info") {
                Web3Info()
            }
        }
    }
}