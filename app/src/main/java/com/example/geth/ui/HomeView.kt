package com.example.geth.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeView(
    buildModelLiveData: MutableLiveData<String>,
    web3ClientVersionLiveData: MutableLiveData<String>,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") {
            MainView(navController, buildModelLiveData, web3ClientVersionLiveData)
        }
        composable("account") {
            AccountView(navController)
        }
    }
}

@Preview
@Composable
fun PreviewHomeView() {
    HomeView(
        buildModelLiveData = MutableLiveData("test1"),
        web3ClientVersionLiveData = MutableLiveData("test2"),
    )
}