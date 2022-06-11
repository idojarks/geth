package com.example.geth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData

@Composable
fun EtherScreen(
    buildModelLiveData: MutableLiveData<String>,
    web3ClientVersionLiveData: MutableLiveData<String>,
) {
    val buildModel = buildModelLiveData.observeAsState("")
    val web3ClientVersion = web3ClientVersionLiveData.observeAsState("")

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "build model: ${buildModel.value}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "web3 client version: ${web3ClientVersion.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EtherScreen(
        buildModelLiveData = MutableLiveData("test1"),
        web3ClientVersionLiveData = MutableLiveData("test2"),
    )
}

