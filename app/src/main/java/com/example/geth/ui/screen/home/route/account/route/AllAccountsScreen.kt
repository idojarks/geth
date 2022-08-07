package com.example.geth.ui.screen.home.route.account.route

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.EtherAccount
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAccountsScreen(
    navController: NavHostController,
) {
    val model = LocalEtherViewModelProvider.current
    val accounts = model.accounts.observeAsState()
    val reloadAccounts = model.reloadAccounts.observeAsState(true)

    val progressStateFlow = remember {
        MutableStateFlow(false)
    }
    val showProgressIndicator by progressStateFlow.collectAsState()

    LaunchedEffect(key1 = reloadAccounts.value) {
        if (!reloadAccounts.value) {
            progressStateFlow.emit(false)
            return@LaunchedEffect
        }

        progressStateFlow.emit(true)

        launch(Dispatchers.IO) {
            model.loadAccounts()
            progressStateFlow.emit(false)
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            model.openAccountScreen.value = false
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.nav_all_accounts))
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("account")
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "new")
                    }
                },
            )
        },
        /*
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("account")
                },
                modifier = Modifier.offset(),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new")
            }
        },

         */
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            accounts.value?.forEach { account ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(text = account.name, style = MaterialTheme.typography.headlineMedium)

                                if (account.isDefault) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "default",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                    )
                                }
                            }
                            Divider(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp))
                            Text(
                                text = "Address",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                            Text(
                                text = account.address,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))

                            AccountBalance(account = account)

                            Spacer(modifier = Modifier.padding(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                            ) {
                                var expanded by remember {
                                    mutableStateOf(false)
                                }

                                if (!account.isDefault) {
                                    OutlinedButton(
                                        onClick = {
                                            model.setDefaultAccount(account)
                                        },
                                    ) {
                                        Icon(imageVector = Icons.Outlined.Star, contentDescription = "default")
                                        Text(text = "Set default", softWrap = false, modifier = Modifier.padding(horizontal = 4.dp))
                                    }
                                }

                                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                    IconButton(
                                        onClick = {
                                            expanded = true
                                        },
                                    ) {
                                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "overflow menu")
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Edit") },
                                            onClick = {
                                                navController.navigate(
                                                    route = "account?address=${account.address}",
                                                )
                                            },
                                            leadingIcon = {
                                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                                            },
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Delete") },
                                            onClick = {
                                                model.deleteAccount(account)
                                            },
                                            leadingIcon = {
                                                Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                                            },
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        if (showProgressIndicator) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading accounts")
                }
            }
        }
    }
}

@Composable
fun AccountBalance(account: EtherAccount) {
    val model = LocalEtherViewModelProvider.current
    val coroutineScope = rememberCoroutineScope()
    val balanceStateFlow = remember {
        MutableStateFlow("")
    }
    val balance by balanceStateFlow.collectAsState()

    coroutineScope.launch(Dispatchers.IO) {
        model.dragon721Service.getBalance(account.address)
            .run {
                balanceStateFlow.emit(this)
            }
    }

    Text(
        text = "Balance",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary,
    )

    if (balance.isEmpty()) {
        val size = with(LocalDensity.current) {
            val size = MaterialTheme.typography.titleMedium.fontSize
            size.toDp()
        }

        CircularProgressIndicator(modifier = Modifier.size(size))
    }
    else {
        Text(
            text = balance,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
fun PreviewAllAccountsScreen() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
    ) {
        AllAccountsScreen(rememberNavController())
    }
}