package com.example.geth.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.geth.Accounts
import com.example.geth.Ether
import com.example.geth.SavedAccount
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountList(
    etherService: Ether,
    model: EtherViewModel? = null
) {
    /*
    val pager = remember {
        Pager(
            PagingConfig(
                50
            )
        ) {
            Accounts(
                etherService
            )
        }
    }
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()

     */
    val ether = remember {
        etherService
    }
    val savedAccounts = model?.savedAccounts?.observeAsState()

    val context = LocalContext.current
    val loadedAccounts = remember {
        SavedAccount().load(
            context = context
        )
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        model?.let {
            AddAccount(it)
        }

        Spacer(
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn {
            loadedAccounts.forEachIndexed { index, s ->
                item {
                    Account(
                        index = index,
                        address = s,
                        ether = ether
                    )
                }
            }

            savedAccounts?.let {
                it.value?.let {
                    savedAccountsLastIndex = it.lastIndex

                    it.forEachIndexed { index, s ->
                        item {
                            Account(
                                index = index,
                                address = s,
                                ether = ether
                            )
                        }
                    }
                }
            }

/*
            if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(
                                Alignment.CenterHorizontally
                            )
                    )
                }
            }

            itemsIndexed(lazyPagingItems) { index, account ->
                account?.let {
                    Account(savedAccountsLastIndex + index, it, ether = ether)
                }
            }

            if (lazyPagingItems.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(
                                Alignment.CenterHorizontally
                            )
                    )
                }
            }

 */
        }
    }
}

@Composable
fun Account(
    index: Int,
    address: String,
    ether: Ether,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                15.dp
            ),
    ) {
        Column(

        ) {
            Text(
                text = "index: $index",
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.secondary
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = address,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val (balance, setBalance) = remember {
                    mutableStateOf("")
                }

                Button(
                    onClick = {
                        setBalance(ether.getBalance(address))
                    },
                ) {
                    Text(
                        text = "Balance"
                    )
                }
                Spacer(
                    modifier = Modifier.padding(
                        10.dp
                    )
                )
                Text(
                    text = balance,
                )
            }

        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun Preview() {
    val etherService = Ether()
        .also {
            it.init(
                "https://ropsten.infura.io/v3/c7c3743a100841048f439743d078ea0d"
            )
        }

    AccountList(
        etherService = etherService
    )
}
