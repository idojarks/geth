package com.example.geth.ui.screen.home.route.account.route

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.AccountSubScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAccountsScreen(
    navController: NavHostController,
) {
    val model = LocalEtherViewModelProvider.current
    val accounts = model.accounts.observeAsState()

    remember {
        model.loadAccounts()
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
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AccountSubScreen.New.route)
                },
                modifier = Modifier.offset(),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new")
            }
        },
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            accounts.value?.forEachIndexed { _, etherAccount ->
                item {
                    Account(
                        account = etherAccount,
                        model = model,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(
    account: EtherAccount,
    model: EtherViewModel,
) {
    /*
    val borderStroke = if (account.isDefault) {
        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primaryContainer)
    } else {
        null
    }

     */



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        //border = borderStroke,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = account.name, style = MaterialTheme.typography.headlineMedium)

                if (account.isDefault) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "default",
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
            Text(
                text = "Balance",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                text = model.dragon721Service.getBalance(account.address),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }

                if (!account.isDefault) {
                    Button(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "default")
                        Text(text = "Set default", softWrap = false, modifier = Modifier.padding(horizontal = 2.dp))
                    }
                }

                Box(modifier = Modifier.wrapContentSize(Alignment.CenterEnd)) {
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
                        DropdownMenuItem(text = { Text("Edit") }, onClick = { /*TODO*/ }, leadingIcon = {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                        })
                        DropdownMenuItem(text = { Text("Delete") }, onClick = { /*TODO*/ }, leadingIcon = {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                        })
                    }
                }

            }
        }
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
    /*
    val model = EtherViewModel(
        accountRepository = InspectionModeAccountRepository(
            mutableListOf(
                EtherAccount(
                    name = "john",
                    address = "0x000000",
                    privateKey = "0x11111",
                ),
            ),
        ),
        dragon721Service = InspectionModeDragon721Service(),
    )

    AllAccountsScreen(rememberNavController())
     */
/*
    Account(
        account = EtherAccount(
            name = "john",
            address = "0x12",
            privateKey = "yong",
            isDefault = true,
        ),
        model = model,
    )

 */
}