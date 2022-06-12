package com.example.geth.ui.screen.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geth.Ether
import com.example.geth.EtherViewModel
import com.example.geth.EtherViewModelInterface
import com.example.geth.SavedAccount

@Composable
fun AccountList(model: EtherViewModelInterface) {
    val ether = remember {
        model.ether
    }
    val savedAccounts = model.accounts.observeAsState()

    val context = LocalContext.current
    val loadedAccounts = remember {
        SavedAccount().load(context = context)
    }

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(300.dp)) {
        AddAccount(model)

        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn {
            loadedAccounts.forEachIndexed { index, s ->
                item {
                    Account(index = index, address = s)
                }
            }

            savedAccounts.let {
                it.value?.let {
                    it.forEachIndexed { index, etherAccount ->
                        item {
                            Account(index = index, address = etherAccount.address, ether = ether)
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
    ether: Ether? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
    ) {
        Column(

        ) {
            Text(text = "index: $index", fontSize = 10.sp, fontStyle = FontStyle.Italic, color = MaterialTheme.colors.secondary)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = address, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val (balance, setBalance) = remember {
                    mutableStateOf("")
                }

                Button(
                    onClick = {
                        ether?.let {
                            setBalance(it.getBalance(address))
                        }
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    val vm = EtherViewModel.previewViewModel
    vm.init()
    /*
    val etherService = Ether().also {
        it.init(
            Url().infuraRopsten,
            viewModel = vm,
        )
    }

     */

    AccountList(
        model = vm,
    )
}
