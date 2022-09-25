package com.example.geth.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.Dragon721ViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.HomeSubScreen
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.home.route.dragon721.Dragon721ArtworkDetailScreen
import com.example.geth.ui.screen.home.route.dragon721.Dragon721TokensScreen
import kotlinx.coroutines.launch

private val drawerItems = listOf(
    HomeSubScreen.Accounts,
    HomeSubScreen.Contracts,
    HomeSubScreen.Dragon721Info,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val model = Dragon721ViewModelProvider.current
    val rootNavController = RootNavController.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerSelectedItem = remember {
        mutableStateOf<HomeSubScreen>(HomeSubScreen.Home)
    }

    val scope = rememberCoroutineScope()

    val clickedArtworkIndex = model.clickedArtworkIndex.observeAsState(-1)

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
                        if (clickedArtworkIndex.value == -1) {
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
                        }
                        else {
                            IconButton(
                                onClick = {
                                    model.clickedArtworkIndex.value = -1
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "back",
                                )
                            }
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                        )
                    },
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if (clickedArtworkIndex.value == -1) {
                    Dragon721TokensScreen()
                }
                else {
                    Dragon721ArtworkDetailScreen(index = clickedArtworkIndex.value)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        Dragon721ViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        HomeScreen()
    }
}