package com.example.geth.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import com.example.geth.R
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.InspectionModeDragon721Service
import com.example.geth.ui.screen.HomeSubScreen
import com.example.geth.ui.screen.Screen
import com.example.geth.ui.screen.home.sub.Dragon721TokensSubScreen
import com.example.geth.ui.screen.home.sub.InfoScreen

private val items = listOf(
    HomeSubScreen.Dragon721Tokens,
    HomeSubScreen.Info,
)

@Composable
fun HomeScreen(
    mainNavController: NavHostController,
) {
    val model = LocalEtherViewModelProvider.current
    val navController = rememberNavController()

    val defaultAccount = model.defaultAccount.observeAsState(EtherAccount())

    val (symbol, setSymbol) = rememberSaveable {
        mutableStateOf("")
    }
    val (artworks, setArtworks) = rememberSaveable {
        mutableStateOf(emptyList<Contracts_Dragon721_sol_Dragon721.Artwork>())
    }

    model.loadDefaultAccount()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    // default account
                    defaultAccount.value?.let {
                        Text(text = it.name)
                    }

                    // account screen
                    IconButton(onClick = {
                        mainNavController.navigate(Screen.Account.route)
                    }) {
                        Icon(imageVector = Screen.Account.icon, contentDescription = Screen.Account.description)
                    }

                    // settings screen
                    IconButton(onClick = {
                        mainNavController.navigate(Screen.Settings.route)
                    }) {
                        Icon(imageVector = Screen.Settings.icon, contentDescription = Screen.Settings.description)
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { subScreen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = subScreen.icon,
                                contentDescription = subScreen.description,
                            )
                        },
                        label = {
                            Text(text = stringResource(id = subScreen.resourceId))
                        },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == subScreen.route
                        } == true,
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
                Dragon721TokensSubScreen(
                    defaultAccount = defaultAccount.value,
                    symbol = symbol,
                    artworks = artworks,
                    onLoadContract = {
                        setSymbol(model.dragon721Service.getSymbol())
                        setArtworks(model.dragon721Service.getAllArtworks())
                    },
                )
            }

            // info screen
            composable(HomeSubScreen.Info.route) {
                InfoScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainView() {
    val model = EtherViewModel(accountRepository = InspectionModeAccountRepository(mutableListOf(
        EtherAccount(
            name = "john",
            address = "0x000000",
            privateKey = "0x11111",
            isDefault = true,
        ),
    )), dragon721Service = InspectionModeDragon721Service())

    CompositionLocalProvider(
        LocalEtherViewModelProvider provides model,
    ) {
        HomeScreen(
            rememberNavController(),
        )
    }
}