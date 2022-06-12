package com.example.geth.ui.screen.home.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geth.EtherViewModel
import com.example.geth.EtherViewModelInterface

@Composable
fun InfoScreen(modelInterface: EtherViewModelInterface) {
    val buildModel = modelInterface.buildModel.observeAsState("")
    val web3ClientVersion = modelInterface.web3ClientVersion.observeAsState("")
    remember {
        modelInterface.init()
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "build model: ${buildModel.value}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "web3 client version: ${web3ClientVersion.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InfoScreen(EtherViewModel.previewViewModel)
}

