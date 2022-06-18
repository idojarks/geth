package com.example.geth.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.geth.R
import com.example.geth.data.EtherViewModel
import com.example.geth.data.EtherViewModelInterface
import com.example.geth.ui.screen.HomeSubScreen
import com.example.geth.ui.screen.Screen
import com.example.geth.ui.screen.home.info.InfoScreen

private val items = listOf(
    HomeSubScreen.Info,
)

@Composable
fun HomeScreen(
    mainNavController: NavHostController,
    modelInterface: EtherViewModelInterface,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
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

                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.description,
                            )
                        },
                        label = {
                            Text(text = stringResource(id = screen.resourceId))
                        },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
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
            startDestination = HomeSubScreen.Info.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            // info screen
            composable(HomeSubScreen.Info.route) {
                InfoScreen(modelInterface = modelInterface)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainView() {
    HomeScreen(
        rememberNavController(),
        EtherViewModel.previewViewModel,
    )
}