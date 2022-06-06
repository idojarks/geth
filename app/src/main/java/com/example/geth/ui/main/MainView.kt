package com.example.geth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

@Composable
fun MainView(
    navController: NavController,
    buildModelLiveData: MutableLiveData<String>,
    web3ClientVersionLiveData: MutableLiveData<String>,
) {
    val buildModel = buildModelLiveData.observeAsState("")
    val web3ClientVersion = web3ClientVersionLiveData.observeAsState("")

    Scaffold(
        topBar = {
            AppBar(title = "ether", navController = navController)
        },
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "build model: ${buildModel.value}")
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "web3 client version: ${web3ClientVersion.value}")
        }
    }
}

@Composable
fun AppBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("account")
            }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "account")
            }

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}

