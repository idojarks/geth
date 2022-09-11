package com.example.geth.ui.screen.home.route.account.route

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.geth.R
import com.example.geth.data.EtherAccount
import com.example.geth.data.LocalEtherViewModelProvider
import com.example.geth.data.getInspectionModeViewModel
import com.example.geth.ui.screen.RootNavController
import com.example.geth.ui.screen.common.AddressBasedCard
import com.example.geth.ui.screen.common.FullScreenList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun AllAccountsScreen(
    navController: NavController,
) {
    val model = LocalEtherViewModelProvider.current

    val accounts = model.accounts.observeAsState()
    //val reloadAccounts = model.reloadAccounts.observeAsState(true)

    val progressStateFlow = remember {
        MutableStateFlow(false)
    }
    val showProgressIndicator by progressStateFlow.collectAsState()

    LaunchedEffect(key1 = accounts) {
        model.accounts.value?.let {
            if (it.isEmpty()) {
                val accountsFromRepo = model.accountRepository.accounts

                if (accountsFromRepo.isEmpty()) {
                    progressStateFlow.emit(false)
                }
                else {
                    model.accounts.postValue(accountsFromRepo)
                }
            }
            else {
                progressStateFlow.emit(false)
            }
        }
    }
/*
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

 */

    FullScreenList(
        title = stringResource(id = R.string.nav_all_accounts),
        onClickAddButton = {
            navController.navigate("account")
        },
        lazyColumnContent = {
            accounts.value?.forEach { account ->
                it.item {
                    AddressBasedCard(
                        name = account.name,
                        address = account.address,
                        privateKey = account.privateKey,
                        isDefault = account.isDefault,
                        onClickDefaultButton = {
                            model.setDefaultAccount(account)
                        },
                        onClickEditMenu = {
                            navController.navigate(
                                route = "account?address=${account.address}",
                            )
                        },
                        onClickDeleteMenu = {
                            model.deleteAccount(account)
                        },
                        extraContents = {
                            AccountBalance(account = account)
                        },
                    )
                }
            }
        },
        showProgressIndicator = showProgressIndicator,
        isEmpty = model.accountRepository.accounts.isEmpty() && !showProgressIndicator,
    )
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

    Spacer(
        modifier = Modifier.padding(4.dp),
    )
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(
        LocalEtherViewModelProvider provides getInspectionModeViewModel(),
        RootNavController provides rememberNavController(),
    ) {
        AllAccountsScreen(
            rememberNavController(),
        )
    }
}