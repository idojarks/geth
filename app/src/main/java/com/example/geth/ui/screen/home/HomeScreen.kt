package com.example.geth.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.HomeSubScreen
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.home.route.dragon721.Dragon721InfoScreen
import com.example.geth.ui.screen.home.route.dragon721.Dragon721TokensScreen
import kotlinx.coroutines.launch

/*
private val items = listOf(
    HomeSubScreen.Dragon721Tokens,
//    HomeSubScreen.ContractSettings,
    HomeSubScreen.Dragon721Info,
)

 */

private val drawerItems = listOf(
    HomeSubScreen.Accounts,
    HomeSubScreen.Contracts,
    HomeSubScreen.Dragon721Info,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val model = LocalEtherViewModelProvider.current
    val rootNavController = RootNavController.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerSelectedItem = remember {
        mutableStateOf<HomeSubScreen>(HomeSubScreen.Home)
    }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "close",
                    )
                }

                model.accountRepository.getDefault()
                    ?.let {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                )
                            },
                            selected = false,
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(
                                NavigationDrawerItemDefaults.ItemPadding,
                            ),
                        )
                    }

                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp),
                )

                drawerItems.forEach {
                    NavigationDrawerItem(
                        icon = {
                            Icon(imageVector = it.getFilledIcon(), contentDescription = it.description)
                        },
                        label = {
                            Text(text = stringResource(id = it.resourceId))
                        },
                        selected = it == drawerSelectedItem.value,
                        onClick = {
                            drawerSelectedItem.value = it
                            rootNavController.navigate(it.route)

                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(
                            NavigationDrawerItemDefaults.ItemPadding,
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "navigation drawer",
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                        )
                    },
                    actions = {
                        /*
                        // default account
                        model.accountRepository.getDefault()
                            ?.let {
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

                        IconButton(
                            onClick = {
                                rootNavController.navigate("contracts")
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "contracts",
                            )
                        }

                         */
/*
                    Box(
                        modifier = Modifier.wrapContentSize(Alignment.TopStart),
                    ) {
                        IconButton(
                            onClick = {
                                expanded = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "overflow",
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(id = R.string.nav_contract))
                                },
                                onClick = {
                                    rootNavController.navigate("contracts")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Email,
                                        contentDescription = "contracts",
                                    )
                                },
                            )
                        }
                    }

 */
                    },
                )
            },
            /*
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

             */
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeSubScreen.Dragon721Tokens.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                // tokens
                composable(HomeSubScreen.Dragon721Tokens.route) {
                    Dragon721TokensScreen()
                }
/*
            // contracts
            composable(HomeSubScreen.ContractSettings.route) {
                ContractsScreen()
            }

 */

                // settings screen
                composable(HomeSubScreen.Dragon721Info.route) {
                    Dragon721InfoScreen()
                }
            }
        }
    }


}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        HomeScreen()
    }
}