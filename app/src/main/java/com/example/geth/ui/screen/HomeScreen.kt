package com.example.geth.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.ui.AccountScreen
import com.example.geth.ui.EtherScreen

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val description: String,
) {
    object Ether : Screen("ether", R.string.nav_ether, Icons.Filled.Home, "ether")
    object Account : Screen("account", R.string.nav_account, Icons.Filled.AccountCircle, "account")
}

private val items = listOf(
    Screen.Ether,
    Screen.Account,
)

@Composable
fun HomeScreen(
    buildModelLiveData: MutableLiveData<String>,
    web3ClientVersionLiveData: MutableLiveData<String>,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(title = "NFT Space")
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Ether.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Ether.route) {
                EtherScreen(buildModelLiveData, web3ClientVersionLiveData)
            }
            composable(Screen.Account.route) {
                AccountScreen(navController)
            }
        }
    }
}

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
            }
        },
    )
}

@Composable
fun BottomBar(
    navController: NavController,
) {
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
}

@Preview
@Composable
fun PreviewMainView() {
    HomeScreen(
        buildModelLiveData = MutableLiveData("test1"),
        web3ClientVersionLiveData = MutableLiveData("test2"),
    )
}