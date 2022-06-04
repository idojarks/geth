package com.example.geth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainView(
    model: EtherViewModel
) {
    val buildModel = model.buildModel.observeAsState("")
    val web3ClientVersion = model.web3ClientVersion.observeAsState("")
    
    Scaffold(
        topBar = {
            HomeAppBar(
                title = "ether",
                openSearch = { /*TODO*/ },
                openFilters = {}
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "build model: ${buildModel.value}"
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "web3 client version: ${web3ClientVersion.value}"
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            AccountList(
                model.ether,
                model
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Contract(
                ether = model.ether
            )
        }
    }
}

@Composable
fun HomeAppBar(
    title: String,
    openSearch: () -> Unit,
    openFilters: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }

            IconButton(onClick = openFilters) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "Filter")
            }
        }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun DefaultPreview() {
    HomeAppBar(
        title = "Ether",
        openSearch = {},
        openFilters = {}
    )
}

