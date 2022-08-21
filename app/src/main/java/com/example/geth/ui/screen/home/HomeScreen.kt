package com.example.geth.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.HomeSubScreen
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.home.route.contract.ContractSettingsScreen
import com.example.geth.ui.screen.home.route.dragon721.Dragon721InfoScreen
import com.example.geth.ui.screen.home.route.dragon721.Dragon721TokensScreen

private val items = listOf(
    HomeSubScreen.Dragon721Tokens,
    HomeSubScreen.ContractSettings,
    HomeSubScreen.Dragon721Info,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val model = LocalEtherViewModelProvider.current
    val rootNavController = RootNavController.current

    val defaultAccount = model.defaultAccount.observeAsState()

    model.loadDefaultAccount()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                    )
                },
                actions = {
                    // default account
                    defaultAccount.value?.let {
                        Text(text = it.name)
                    }

                    IconButton(
                        onClick = {
                            rootNavController.navigate("account")
                            //model.openAccountScreen.value = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "account",
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { subScreen ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == subScreen.route
                    } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected) subScreen.getFilledIcon() else subScreen.getOutlinedIcon(),
                                contentDescription = subScreen.description,
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = subScreen.resourceId),
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(subScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeSubScreen.Dragon721Tokens.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            // tokens
            composable(HomeSubScreen.Dragon721Tokens.route) {
                Dragon721TokensScreen(defaultAccount = defaultAccount.value)
            }
            // contract
            composable(HomeSubScreen.ContractSettings.route) {
                ContractSettingsScreen()
            }
            // settings screen
            composable(HomeSubScreen.Dragon721Info.route) {
                Dragon721InfoScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainView() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides navController,
    ) {
        HomeScreen()
    }
}