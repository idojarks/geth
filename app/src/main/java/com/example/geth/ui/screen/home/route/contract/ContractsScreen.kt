package com.example.geth.ui.screen.home.route.contract

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.ui.screen.home.route.contract.route.AllContractsScreen
import com.example.geth.ui.screen.home.route.contract.route.ContractDetailScreen

@Composable
fun ContractsScreen() {
    val navController = rememberNavController()
    val model = LocalEtherViewModelProvider.current

    NavHost(
        navController = navController,
        startDestination = "allContracts",
    ) {
        composable("allContracts") {
            AllContractsScreen(navController)
        }
        composable(
            route = "contract?address={address}",
            arguments = listOf(
                navArgument("address") {
                    nullable = true
                },
            ),
        ) {
            val contract = it.arguments?.getString("address")
                ?.let { address ->
                    model.contractRepository.get(address)
                }

            ContractDetailScreen(navController = navController, contract = contract)
        }
    }
}