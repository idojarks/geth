package com.example.geth.ui.screen.account.sub

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherViewModel
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.service.account.InspectionModeAccountRepository
import com.example.geth.service.blockchain.InspectionModeDragon721Service
import com.example.geth.ui.screen.AccountSubScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListSubScreen(
    mainNavController: NavController,
    navController: NavController,
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
                            navController.popBackStack(navController.graph.startDestinationId, true)
                            mainNavController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "home",
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = AccountSubScreen.AccountList.resourceId),
                    )
                },
                actions = {
                    // new account
                    IconButton(
                        onClick = {
                            navController.navigate(AccountSubScreen.NewAccount.route)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "new account",
                        )
                    }

                },
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
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
    val borderStroke = if (account.isDefault) {
        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primaryContainer)
    } else {
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        border = borderStroke,
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
        ) {
            Text(
                text = account.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(6.dp),
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = account.address,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp),
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val (balance, setBalance) = remember {
                    mutableStateOf("")
                }

                Button(
                    onClick = {
                        setBalance(model.dragon721Service.getBalance(account.address))
                    },
                ) {
                    Text(text = "Balance")
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = balance,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAccountList() {
    val model = EtherViewModel(accountRepository = InspectionModeAccountRepository(mutableListOf(
        EtherAccount(
            name = "john",
            address = "0x000000",
            privateKey = "0x11111",
        ),
    )), dragon721Service = InspectionModeDragon721Service())

    Account(
        account = EtherAccount(
            name = "john",
            address = "adfa",
            privateKey = "dsfsd",
            isDefault = false,
        ),
        model = model,
    )
    /*
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides model,
    ) {
        AccountListSubScreen(
            mainNavController = rememberNavController(),
            navController = rememberNavController(),
        )
    }

     */
}
